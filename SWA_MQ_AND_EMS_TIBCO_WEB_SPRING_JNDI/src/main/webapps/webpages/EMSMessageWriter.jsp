<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS Message Publisher</title>
</head>
<body>
    <form action="TibcoEMSPublisherServlet" method="post">
         Message Input <input id="messageInputId" type="text" name="messageInput" placeholder="provide message here" required>
    <br />
    <input type="submit" value="PublishMessage" />
    </form>
</body>
</html>