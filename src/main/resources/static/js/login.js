// const loginForm =
//     document.getElementById("loginForm");
//
// const message =
//     document.getElementById("message");
//
//
// loginForm.addEventListener(
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
//         message.textContent =
//             "Logging in...";
//
//         try {
//
//             const response =
//                 await fetch(
//                     "/api/auth/login",
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
//                     "Login failed";
//
//                 return;
//             }
//
//             window.location.href =
//                 "/index.html";
//
//         } catch (error) {
//
//             message.textContent =
//                 "Unable to connect to server.";
//         }
//     }
// );

const loginForm =
    document.getElementById("loginForm");

const message =
    document.getElementById("message");


loginForm.addEventListener(
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


        if (!username || !password) {

            message.textContent =
                "Username and password are required.";

            return;
        }


        message.textContent =
            "Logging in...";


        try {

            const response =
                await fetch(
                    "/api/auth/login",
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


            // =========================================
            // READ RESPONSE SAFELY
            // =========================================

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
                    "Server returned non-JSON response:",
                    responseText
                );


                message.textContent =
                    "Server returned an invalid response. HTTP " +
                    response.status;


                return;
            }


            // =========================================
            // LOGIN FAILED
            // =========================================

            if (!response.ok) {

                console.error(
                    "Login failed:",
                    response.status,
                    data
                );


                message.textContent =
                    data.error ||
                    "Login failed. HTTP " +
                    response.status;


                return;
            }


            // =========================================
            // LOGIN SUCCESS
            // =========================================

            console.log(
                "Login successful:",
                data
            );


            message.textContent =
                "Login successful. Opening notes...";


            // Give browser time to store session cookie
            setTimeout(
                function () {

                    window.location.href =
                        "/index.html";

                },
                300
            );
        }


            // =============================================
            // REAL NETWORK ERROR
            // =============================================

        catch (error) {

            console.error(
                "LOGIN NETWORK ERROR:",
                error
            );


            message.textContent =
                "Cannot reach the server. " +
                "Make sure Spring Boot is running on port 8082.";
        }
    }
);