<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<form action="AuthServlet" id="login-form" method="post" >
    <label for="login_id">Login ID:</label>
    <input type="text" id="login_id" name="login_id"><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password"><br>
    <button type="submit">Login</button>
</form>

<script>
    document.getElementById('login-form').onsubmit = async function (event) {
        event.preventDefault();
        const formData = new FormData(this);
        const response = await fetch('AuthServlet', {
            method: 'POST',
            body: formData
        });

        const result = await response.json();
        if (response.ok) {
            localStorage.setItem('token', result.token);
            window.location.href = 'customer.html';
        } else {
            document.getElementById('message').innerText = result.message;
        }
    }
</script>
</body>
</html>
