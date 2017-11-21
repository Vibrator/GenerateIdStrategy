<%@ page language="java" contentType="text/html; charset=UTF-8""
    pageEncoding="UTF-8""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>授权验证</h1>
	<p>你确认要授权给Link用户访问该资源的权限吗？</p>
	<form id="confirmationForm" name="confirmationForm" action="/oauth/authorize" method="post">
		<input name="user_oauth_approval" value="true" type="hidden">
			<ul>
				<li>
					<div class="form-group"><!-- scope.app:  -->
						<input type="radio" name="scope.app" value="true" checked="">确认 
						<input type="radio" name="scope.app" value="false">拒绝
					</div>
				</li>
			</ul>
			<label>
				<input name="authorize" value="Authorize" type="submit">
			</label>
	</form>

</body>
</html>