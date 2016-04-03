<%@include file="../include/taglib_includes.jsp"%>
<%-- <div id="main-header">
	<div class="login-fflogo" title="First Flight Couriers Ltd."></div>
	<c:if test="${!empty welcomeUserName}">
		<div class="welcomeMsg">

			Welcome to UDAAN, .${userId}...<strong> <c:if test="${userId ne null}">
						${welcomeUserName}&nbsp;|
						 <a href="${contextroot }/logout.do" title="Logout"
						style="color: red;"><strong>Logout</strong></a>
					<br />
				</c:if>
			</strong> Logged In Office: <strong>${user.officeTo.officeTypeTO.offcTypeDesc}-${user.officeTo.officeName}</strong>&nbsp;|
			<jsp:useBean id="now" class="java.util.Date" />
			<fmt:formatDate value="${now}" pattern="EEE, d MMM yyyy hh:mm a" />
			<br /> <a href="forgotPassword.do"
				style="color: white;"> Change Password</a>

		</div>
	</c:if>
	<div class="login-apptext"
		title="Web Tracking"></div>
</div> --%>


  <div id="main-header-int">
    <div class="fflogo" title="First Flight Couriers Ltd."></div>
    <div class="welcomeMsg"> <!-- Welcome to UDAAN --> <!-- <strong>Mr. John Mathew</strong> --> | <a href="#" title="Logout" style="color:red;"><strong><!-- Logout --></strong></a> <br/>
             <!--  Tuesday, Oct 10th, 2012&nbsp;&nbsp;&nbsp;9:00:55 IST -->  </div>
  </div>

  