<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>Sign in &middot; Hehe System</title>
    <link rel="stylesheet" href="/css/bootstrap.css" />
    <link rel="stylesheet" href="/css/bootstrap-responsive.css"/>
    <script src="/js/bootstrap/bootstrap.js" type="text/javascript"></script>
    <script src="/js/bootstrap/prettify.js" type="text/javascript"></script>
     <script src="/js/jqery/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap/jqBootstrapValidation.js" type="text/javascript"></script>

    <style type="text/css">
          body {
                   padding-bottom: 40px;
                   color:#1C1C1C;
               }
        </style>
        <script>
          $(function () {
           $("input,textarea,select").jqBootstrapValidation(
                              {
                                  preventSubmit: true,
                                  submitError: function($form, event, errors) {
                                      // Here I do nothing, but you could do something like display
                                      // the error messages to the user, log, etc.
                                  },

                                  filter: function() {
                                      return $(this).is(":visible");
                                  }
                              }
                          );


          } );
        </script>
</head>
<body>

          <div class="navbar">
          <div class="navbar-inner">
          <a class="brand" href="#">d-sys</a>
          <ul class="nav">
          <li class="active"><a href="#">登录</a></li>
          <li><a href="#">首页</a></li>
          <li><a href="#">讨论</a></li>
          </ul>
          </div>
          </div>

     <form class="form-horizontal" action="/login" method="POST" >
      <div class="control-group">

     <div class="controls">
      <legend>登录</legend>
     </div>
     </div>
     <div class="control-group">
     <label class="control-label">Email</label>
     <div class="controls">
     <input name="mail"  id="inputEmail" placeholder="Email"  type="email"
      required>

     </div>
     </div>
     <div class="control-group">
     <label class="control-label" >Password</label>
     <div class="controls">
     <input type="password" minlength="6" name="password"
      id="inputPassword"
      maxlength="20" required>
     </div>
     </div>

      <div class="control-group">
          <label class="control-label" >验证码</label>
          <div class="controls">
      <input type="text" name="validateCode" />&nbsp;&nbsp;<img id="validateCodeImg" src="/validateCode" />&nbsp;&nbsp;<a href="#" onclick="javascript:reloadValidateCode();">看不清？</a></li>
          </div>
          </div>

     <div class="control-group">
     <div class="controls">
     <label class="checkbox">
     <input type="checkbox"> Remember me
     </label>
     <button type="submit" class="btn btn-info">Sign in</button>
     </div>
     </div>
     </form>
   <script type="text/javascript">
      <!--
      function reloadValidateCode(){
          $("#validateCodeImg").attr("src","/validateCode?data=" + new Date() + Math.floor(Math.random()*24));
      }
      //-->
      </script>
</body>
</html>
