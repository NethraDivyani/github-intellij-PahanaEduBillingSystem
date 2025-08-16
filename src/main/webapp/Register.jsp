<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>PahanaEdu Book Shop - Registration</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: Arial, sans-serif;
            background: linear-gradient(to bottom right, #1e3c72, #2a5298);
            background-size: cover;
            color: white;
        }

        .overlay {
            background-color: rgba(0,0,0,0.6);
            height: 100%;
            width: 100%;
            position: fixed;
            top: 0;
            left: 0;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 20px;
            box-sizing: border-box;
        }

        .title-banner {
            font-size: 3em;
            font-weight: bold;
            margin-bottom: 40px;
            letter-spacing: 4px;
            text-shadow: 2px 2px 6px #000;
        }

        .login-container {
            background-color: rgba(255,255,255,0.1);
            padding: 30px 40px;
            border-radius: 10px;
            min-width: 320px;
            box-shadow: 0 0 15px rgba(0,0,0,0.8);
        }

        .login-container label {
            display: block;
            margin: 15px 0 5px;
            font-weight: 600;
        }

        input, select, button {
            width: 100%;
            padding: 10px;
            font-size: 1rem;
            border: none;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input, select {
            margin-bottom: 15px;
        }

        button {
            background-color: #28a745;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
            font-weight: bold;
        }

        button:hover {
            background-color: #1e7e34;
        }

        @media (max-width: 480px) {
            .login-container {
                width: 100%;
                padding: 20px;
                min-width: auto;
            }
            .title-banner {
                font-size: 2em;
                margin-bottom: 25px;
            }
        }
    </style>
</head>
<body>
<div class="overlay">
    <div class="title-banner">Register</div>

    <div class="login-container">
        <form method="post" action="RegisterServlet" autocomplete="off">
            <label for="name">Full Name:</label>
            <input type="text" id="name" name="name" required />

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required />

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required />

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required />

            <label for="role">Role:</label>
            <select id="role" name="role" required>
                <option value="" disabled selected>-- Select Role --</option>
                <option value="admin">Admin</option>
                <option value="cashier">Cashier</option>
            </select>

            <button type="submit">Register</button>
        </form>
    </div>
</div>


</body>
</html>
