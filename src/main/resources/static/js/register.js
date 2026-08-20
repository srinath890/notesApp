// const registerForm =
//     document.getElementById("registerForm");
//
// const message =
//     document.getElementById("message");
//
//
// registerForm.addEventListener(
//     "submit",
//     async function (event) {
//
//         event.preventDefault();
//
//         const username =
//             document.getElementById("username")
//                 .value
//                 .trim();
//
//         const password =
//             document.getElementById("password")
//                 .value;
//
//         const confirmPassword =
//             document.getElementById("confirmPassword")
//                 .value;
//
//         if (password !== confirmPassword) {
//
//             message.textContent =
//                 "Passwords do not match.";
//
//             return;
//         }
//
//         if (password.length < 8) {
//
//             message.textContent =
//                 "Password must contain at least 8 characters.";
//
//             return;
//         }
//
//         message.textContent =
//             "Creating account...";
//
//         try {
//
//             const response =
//                 await fetch(
//                     "/api/auth/register",
//                     {
//                         method: "POST",
//
//                         headers: {
//                             "Content-Type":
//                                 "application/json"
//                         },
//
//                         body: JSON.stringify({
//                             username,
//                             password
//                         })
//                     }
//                 );
//
//             const data =
//                 await response.json();
//
//             if (!response.ok) {
//
//                 message.textContent =
//                     data.error ||
//                     "Registration failed";
//
//                 return;
//             }
//
//             message.textContent =
//                 "Account created successfully.";
//
//             setTimeout(
//                 function () {
//
//                     window.location.href =
//                         "/login.html";
//
//                 },
//                 1000
//             );
//
//         } catch (error) {
//
//             message.textContent =
//                 "Unable to connect to server.";
//         }
//     }
// );

const registerForm =
    document.getElementById("registerForm");

const message =
    document.getElementById("message");


registerForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const username =
            document.getElementById("username")
                .value
                .trim();


        const password =
            document.getElementById("password")
                .value;


        const confirmPassword =
            document.getElementById("confirmPassword")
                .value;


        // =========================================
        // VALIDATION
        // =========================================

        if (!username) {

            message.textContent =
                "Username is required.";

            return;
        }


        if (!password) {

            message.textContent =
                "Password is required.";

            return;
        }


        if (password !== confirmPassword) {

            message.textContent =
                "Passwords do not match.";

            return;
        }


        if (password.length < 8) {

            message.textContent =
                "Password must contain at least 8 characters.";

            return;
        }


        message.textContent =
            "Creating account...";


        try {

            const response =
                await fetch(
                    "/api/auth/register",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json",

                            "Accept":
                                "application/json"
                        },

                        credentials:
                            "same-origin",

                        body:
                            JSON.stringify({
                                username:
                                username,

                                password:
                                password
                            })
                    }
                );


            const responseText =
                await response.text();


            let data = {};


            try {

                if (responseText) {

                    data =
                        JSON.parse(
                            responseText
                        );
                }

            } catch (jsonError) {

                console.error(
                    "Invalid server response:",
                    responseText
                );


                message.textContent =
                    "Server returned an invalid response. HTTP " +
                    response.status;


                return;
            }


            // =========================================
            // REGISTRATION FAILED
            // =========================================

            if (!response.ok) {

                console.error(
                    "Registration failed:",
                    response.status,
                    data
                );


                message.textContent =
                    data.error ||
                    "Registration failed. HTTP " +
                    response.status;


                return;
            }


            // =========================================
            // SUCCESS
            // =========================================

            console.log(
                "Registration successful:",
                data
            );


            message.textContent =
                "Account created successfully.";


            setTimeout(
                function () {

                    window.location.href =
                        "/login.html";

                },
                1000
            );
        }


            // =========================================
            // NETWORK ERROR
            // =========================================

        catch (error) {

            console.error(
                "REGISTER NETWORK ERROR:",
                error
            );


            message.textContent =
                "Cannot reach the server. " +
                "Make sure Spring Boot is running on port 8082.";
        }
    }
);