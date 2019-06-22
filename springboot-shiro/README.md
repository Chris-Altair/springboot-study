关于共享session的总结与思考

场景：springboot-shiro-redis-nginx-tomcat集群 实现session共享
ShiroConfig有配置缓存（貌似好像没生效???）
nginx（轮训模式）
初始状态：浏览器登录后进入index页

#同一个浏览器登录后会将session存储在redis中
redis中的清除session条件：
	①调用logout接口
	②redis中session超过timeout时间（这里设置的是自动删除无效session）
	③redsi异常（这里基本上不考虑这种情况）

if 应用重启 and redis中对应session未失效
	重启后且在不关闭浏览器下调用/test 接口：还是会显示重启前的session（即用户是感觉不出应用重启的）
	#如果是单机应用重启，session是存在内存中的，重启会直接清除掉
if (重启浏览器 or 打开其他浏览器输入接口) and redis中对应session未失效
	浏览器直接输入/test：会跳到login页面（这时原来的session还在redis中）
	->推测服务器通过request-headers中的cookie信息中的sessionId来从redis中搜索session--
	--if 找到session
		返回/test接口
	  else
	  	重定向到/login

准备测试上述猜测：登陆后通过开发者工具发现请求/test的Request Header的cookie属性中的JSESSIONID
与redis中的key中包含的相同证明猜想

上面的Cookie: Idea-8306591a=2d23dccd-3912-4c5f-8059-a7177d431803; JSESSIONID=0026f924-b1e8-44cf-9dc9-bd07179f64cb

===========================
测试条件：
①不使用rememberMe
②springboot-shiro-redis
打开浏览器访问get请求/login就会产生一个cookie（有时不会创建，在登录后才会创建，原因未知），
并且会创建一个session(注意还未登录！！！），其id值为cookie（叫会话cookie）中的SHARE_JSESSIONID(shiroConfig中配置的名字)的值，
并且对应的session信息会被存到redis上，之后用户的登录与使用都会使用这个session；

if 未登出就退出浏览器：
	则redis中的session信息还在，但重新打开浏览器后会发现原来的cookie已经没有了，
	因为sessionManager.setSessionIdCookie(sessionIdCookie());
	sessionIdCookie方法默认的MaxAge=-1表示关闭浏览器就清除cookie
	这里的cookie是跟session关联的，所以出于安全的考虑MaxAge=-1；
	并且输入url后会生成新的cookie，
	创建一个新的session并存到redis中（原来的无效session超时后会自动删除，在sessionManage中可设置）
	
if 登出：
	会清除跟session关联的cookie信息，并删除redis中的session

总结:session与是否登录无关，链接服务器就会创建(区别是这个session里面是否存有效数据)；
链接服务器浏览器会产生会话cookie，
服务器根据链接request header中cookie的jsessionid(名字可根据代码修改)搜索session，没找到会创建,session的管理与存储跟代码实现有关；
session的创建与关闭跟语言及代码具体实现有关，跟使用的框架也有关；

登录后30分钟(代码设置的)不动，redis会自动将session删除，这时刷新index页面会生成新的cookie，服务器会创建新的session
两个浏览器之间的session操作是没有影响的，
在一个浏览器登录后，调用/login接口输入另一个用户信息

=============================
测试rememberMe
登录时若勾选rememberMe，登录后浏览器会生成rememberMe cookie（超过最大时间或手动清除cookie会消失） 和会话cookie（关闭浏览器会消失），
并且会根据会话cookie的id搜索redis的session信息没有就创建（不会根据rememberMe cookie存储session！！！）
登出会清除所有cookie和关联的session；
直接关闭浏览器(不登出)，在打开浏览器输入/index页面会直接进入（rememberMe）index页面，会创建一个新的会话session
（原来的会话session仍然存在，直到过期或手动删除才消失）