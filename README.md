# antplatform-admin

- 前端请求登录，传入用户名userName和密码password和令牌token；
- 后端进入jwtFilter拦截，判断当前请求中是否是登录请求（可以通过是否存在token,也可以通过判断请求的地址是否是登录地址）
- 如果是登录请求，就不执行shiro认证和授权，直接进入控制器进行帐号和密码校验，校验成功生成token返回；
- 如果是非登录请求，jwtFilter执行executeLogin方法进入自定义realm进行认证doGetAuthenticationInfo（认证不通过，抛出异常，异常的处理稍后详细说明）和授权doGetAuthorizationInfo，都通过然后进入自己的控制器；