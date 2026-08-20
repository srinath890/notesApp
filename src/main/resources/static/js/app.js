// let notes = [];
//
// let currentNoteId = null;
//
//
// const notesList =
//     document.getElementById("notesList");
//
// const editor =
//     document.getElementById("editor");
//
// const emptyState =
//     document.getElementById("emptyState");
//
// const noteTitle =
//     document.getElementById("noteTitle");
//
// const noteContent =
//     document.getElementById("noteContent");
//
// const saveStatus =
//     document.getElementById("saveStatus");
//
// const currentUsername =
//     document.getElementById("currentUsername");
//
// const imageInput =
//     document.getElementById("imageInput");
//
//
// /* =========================
//    START APPLICATION
//    ========================= */
//
// document.addEventListener(
//     "DOMContentLoaded",
//     async function () {
//
//         const loggedIn =
//             await checkLogin();
//
//         if (!loggedIn) {
//
//             window.location.href =
//                 "/login.html";
//
//             return;
//         }
//
//         await loadNotes();
//     }
// );
//
//
// /* =========================
//    CHECK LOGIN
//    ========================= */
//
// async function checkLogin() {
//
//     try {
//
//         const response =
//             await fetch("/api/auth/me");
//
//         if (!response.ok) {
//             return false;
//         }
//
//         const data =
//             await response.json();
//
//         currentUsername.textContent =
//             data.username;
//
//         return true;
//
//     } catch (error) {
//
//         return false;
//     }
// }
//
//
// /* =========================
//    LOAD NOTES
//    ========================= */
//
// async function loadNotes() {
//
//     try {
//
//         const response =
//             await fetch("/api/notes");
//
//         if (!response.ok) {
//
//             window.location.href =
//                 "/login.html";
//
//             return;
//         }
//
//         notes =
//             await response.json();
//
//         renderNotes();
//
//     } catch (error) {
//
//         console.error(error);
//     }
// }
//
//
// /* =========================
//    DISPLAY NOTES
//    ========================= */
//
// function renderNotes() {
//
//     notesList.innerHTML = "";
//
//     if (notes.length === 0) {
//
//         return;
//     }
//
//     notes.forEach(function (note) {
//
//         const item =
//             document.createElement("div");
//
//         item.className =
//             "note-list-item";
//
//         if (currentNoteId === note.id) {
//
//             item.classList.add("active");
//         }
//
//         const title =
//             document.createElement("div");
//
//         title.className =
//             "note-list-title";
//
//         title.textContent =
//             note.title;
//
//         const date =
//             document.createElement("div");
//
//         date.className =
//             "note-list-date";
//
//         date.textContent =
//             formatDate(note.updatedAt);
//
//         item.appendChild(title);
//
//         item.appendChild(date);
//
//         item.addEventListener(
//             "click",
//             function () {
//
//                 openNote(note.id);
//             }
//         );
//
//         notesList.appendChild(item);
//     });
// }
//
//
// /* =========================
//    OPEN NOTE
//    ========================= */
//
// async function openNote(id) {
//
//     try {
//
//         const response =
//             await fetch(
//                 `/api/notes/${id}`
//             );
//
//         if (!response.ok) {
//
//             alert("Unable to open note.");
//
//             return;
//         }
//
//         const note =
//             await response.json();
//
//         currentNoteId =
//             note.id;
//
//         noteTitle.value =
//             note.title;
//
//         noteContent.innerHTML =
//             note.content;
//
//         emptyState.style.display =
//             "none";
//
//         editor.style.display =
//             "flex";
//
//         saveStatus.textContent =
//             "Saved";
//
//         renderNotes();
//
//     } catch (error) {
//
//         console.error(error);
//     }
// }
//
//
// /* =========================
//    NEW NOTE
//    ========================= */
//
// function createNewNote() {
//
//     currentNoteId = null;
//
//     noteTitle.value = "";
//
//     noteContent.innerHTML = "";
//
//     emptyState.style.display =
//         "none";
//
//     editor.style.display =
//         "flex";
//
//     saveStatus.textContent =
//         "New note";
//
//     renderNotes();
//
//     noteTitle.focus();
// }
//
//
// /* =========================
//    SAVE NOTE
//    ========================= */
//
// async function saveNote() {
//
//     const title =
//         noteTitle.value.trim();
//
//     const content =
//         noteContent.innerHTML;
//
//     if (!title) {
//
//         alert(
//             "Please enter a note title."
//         );
//
//         return;
//     }
//
//     saveStatus.textContent =
//         "Saving...";
//
//     try {
//
//         let response;
//
//         if (currentNoteId === null) {
//
//             response =
//                 await fetch(
//                     "/api/notes",
//                     {
//                         method: "POST",
//
//                         headers: {
//                             "Content-Type":
//                                 "application/json"
//                         },
//
//                         body: JSON.stringify({
//                             title,
//                             content
//                         })
//                     }
//                 );
//
//         } else {
//
//             response =
//                 await fetch(
//                     `/api/notes/${currentNoteId}`,
//                     {
//                         method: "PUT",
//
//                         headers: {
//                             "Content-Type":
//                                 "application/json"
//                         },
//
//                         body: JSON.stringify({
//                             title,
//                             content
//                         })
//                     }
//                 );
//         }
//
//         const data =
//             await response.json();
//
//         if (!response.ok) {
//
//             saveStatus.textContent =
//                 "Save failed";
//
//             alert(
//                 data.error ||
//                 "Unable to save note."
//             );
//
//             return;
//         }
//
//         currentNoteId =
//             data.id;
//
//         saveStatus.textContent =
//             "Saved";
//
//         await loadNotes();
//
//         renderNotes();
//
//     } catch (error) {
//
//         console.error(error);
//
//         saveStatus.textContent =
//             "Save failed";
//     }
// }
//
//
// /* =========================
//    DELETE NOTE
//    ========================= */
//
// async function deleteNote() {
//
//     if (currentNoteId === null) {
//
//         return;
//     }
//
//     const confirmDelete =
//         confirm(
//             "Are you sure you want to delete this note?"
//         );
//
//     if (!confirmDelete) {
//
//         return;
//     }
//
//     try {
//
//         const response =
//             await fetch(
//                 `/api/notes/${currentNoteId}`,
//                 {
//                     method: "DELETE"
//                 }
//             );
//
//         if (!response.ok) {
//
//             alert(
//                 "Unable to delete note."
//             );
//
//             return;
//         }
//
//         currentNoteId = null;
//
//         noteTitle.value = "";
//
//         noteContent.innerHTML = "";
//
//         editor.style.display =
//             "none";
//
//         emptyState.style.display =
//             "flex";
//
//         await loadNotes();
//
//     } catch (error) {
//
//         console.error(error);
//     }
// }
//
//
// /* =========================
//    LOGOUT
//    ========================= */
//
// async function logout() {
//
//     try {
//
//         await fetch(
//             "/api/auth/logout",
//             {
//                 method: "POST"
//             }
//         );
//
//         window.location.href =
//             "/login.html";
//
//     } catch (error) {
//
//         console.error(error);
//
//         window.location.href =
//             "/login.html";
//     }
// }
//
//
// /* =========================
//    TEXT FORMATTING
//    ========================= */
//
// document
//     .querySelectorAll(
//         "[data-command]"
//     )
//     .forEach(function (button) {
//
//         button.addEventListener(
//             "click",
//             function () {
//
//                 const command =
//                     button.dataset.command;
//
//                 document.execCommand(
//                     command,
//                     false,
//                     null
//                 );
//
//                 noteContent.focus();
//             }
//         );
//     });
//
//
// /* =========================
//    HEADINGS
//    ========================= */
//
// document
//     .getElementById("headingSelect")
//     .addEventListener(
//         "change",
//         function () {
//
//             const value =
//                 this.value;
//
//             document.execCommand(
//                 "formatBlock",
//                 false,
//                 value
//             );
//
//             noteContent.focus();
//         }
//     );
//
//
// /* =========================
//    IMAGE UPLOAD
//    ========================= */
//
// imageInput.addEventListener(
//     "change",
//     async function () {
//
//         const file =
//             this.files[0];
//
//         if (!file) {
//             return;
//         }
//
//         if (!file.type.startsWith("image/")) {
//
//             alert(
//                 "Please select an image."
//             );
//
//             return;
//         }
//
//         if (file.size > 10 * 1024 * 1024) {
//
//             alert(
//                 "Image must be smaller than 10 MB."
//             );
//
//             return;
//         }
//
//         saveStatus.textContent =
//             "Uploading image...";
//
//         const formData =
//             new FormData();
//
//         formData.append(
//             "file",
//             file
//         );
//
//         try {
//
//             const response =
//                 await fetch(
//                     "/api/files/upload",
//                     {
//                         method: "POST",
//                         body: formData
//                     }
//                 );
//
//             const data =
//                 await response.json();
//
//             if (!response.ok) {
//
//                 alert(
//                     data ||
//                     "Image upload failed."
//                 );
//
//                 return;
//             }
//
//             const image =
//                 document.createElement("img");
//
//             image.src =
//                 "/api/files/" +
//                 data.path;
//
//             image.alt =
//                 "Uploaded image";
//
//             noteContent.appendChild(
//                 image
//             );
//
//             saveStatus.textContent =
//                 "Image uploaded";
//
//         } catch (error) {
//
//             console.error(error);
//
//             alert(
//                 "Image upload failed."
//             );
//
//         } finally {
//
//             imageInput.value = "";
//         }
//     }
// );
//
//
// /* =========================
//    BUTTON EVENTS
//    ========================= */
//
// document
//     .getElementById("newNoteButton")
//     .addEventListener(
//         "click",
//         createNewNote
//     );
//
// document
//     .getElementById("emptyNewButton")
//     .addEventListener(
//         "click",
//         createNewNote
//     );
//
// document
//     .getElementById("saveButton")
//     .addEventListener(
//         "click",
//         saveNote
//     );
//
// document
//     .getElementById("deleteButton")
//     .addEventListener(
//         "click",
//         deleteNote
//     );
//
// document
//     .getElementById("logoutButton")
//     .addEventListener(
//         "click",
//         logout
//     );
//
//
// /* =========================
//    DATE FORMAT
//    ========================= */
//
// function formatDate(dateString) {
//
//     if (!dateString) {
//         return "";
//     }
//
//     const date =
//         new Date(dateString);
//
//     return date.toLocaleString(
//         "en-IN",
//         {
//             day: "2-digit",
//             month: "short",
//             year: "numeric",
//             hour: "2-digit",
//             minute: "2-digit"
//         }
//     );
// }

let notes = [];

let selectedNoteId = null;

let saveTimer = null;

let searchText = "";

let saveInProgress = false;


// =====================================================
// PAGE LOAD
// =====================================================

document.addEventListener(
    "DOMContentLoaded",
    function () {

        setupEvents();

        loadNotes();

    }
);


// =====================================================
// EVENTS
// =====================================================

function setupEvents() {

    const newButton =
        document.getElementById(
            "newNoteButton"
        );

    if (newButton) {

        newButton.addEventListener(
            "click",
            createNewNote
        );
    }


    const title =
        document.getElementById(
            "noteTitle"
        );

    if (title) {

        title.addEventListener(
            "input",
            startAutoSave
        );
    }


    const content =
        document.getElementById(
            "noteContent"
        );

    if (content) {

        content.addEventListener(
            "input",
            startAutoSave
        );
    }


    const deleteButton =
        document.getElementById(
            "deleteButton"
        );

    if (deleteButton) {

        deleteButton.addEventListener(
            "click",
            deleteNote
        );
    }


    const search =
        document.getElementById(
            "searchInput"
        );

    if (search) {

        search.addEventListener(
            "input",
            function () {

                searchText =
                    search.value
                        .toLowerCase()
                        .trim();

                renderSidebar();

            }
        );
    }
}


// =====================================================
// LOAD NOTES
// =====================================================

async function loadNotes() {

    try {

        const response =
            await fetch(
                "/api/notes",
                {
                    method: "GET",
                    credentials: "same-origin"
                }
            );


        if (response.status === 401) {

            window.location.href =
                "/login.html";

            return;
        }


        if (!response.ok) {

            throw new Error(
                "Unable to load notes"
            );
        }


        notes =
            await response.json();


        sortNotes();


        renderSidebar();


        if (notes.length > 0) {

            await openNote(
                notes[0].id
            );

        }

    } catch (error) {

        console.error(
            "Load notes error:",
            error
        );
    }
}


// =====================================================
// SORT
// =====================================================

function sortNotes() {

    notes.sort(
        function (a, b) {

            const dateA =
                new Date(
                    a.updatedAt ||
                    a.createdAt ||
                    a.noteDate
                );


            const dateB =
                new Date(
                    b.updatedAt ||
                    b.createdAt ||
                    b.noteDate
                );


            return dateB - dateA;

        }
    );
}


// =====================================================
// SIDEBAR
// =====================================================

function renderSidebar() {

    const list =
        document.getElementById(
            "notesList"
        );


    if (!list) {

        return;
    }


    list.innerHTML = "";


    const filtered =
        notes.filter(
            function (note) {

                if (!searchText) {

                    return true;
                }


                const title =
                    (
                        note.title || ""
                    ).toLowerCase();


                const content =
                    (
                        note.content || ""
                    ).toLowerCase();


                return (
                    title.includes(
                        searchText
                    )
                    ||
                    content.includes(
                        searchText
                    )
                );
            }
        );


    if (filtered.length === 0) {

        list.innerHTML = `
            <div class="no-notes">
                No saved notes
            </div>
        `;

        return;
    }


    filtered.forEach(
        function (note) {

            const item =
                document.createElement(
                    "div"
                );


            item.className =
                "note-item";


            if (
                Number(note.id) ===
                Number(selectedNoteId)
            ) {

                item.classList.add(
                    "active"
                );
            }


            item.innerHTML = `

                <div class="note-item-title">
                    ${escapeHtml(
                note.title ||
                "Untitled Note"
            )}
                </div>

                <div class="note-item-date">
                    ${formatDate(
                note.updatedAt ||
                note.createdAt ||
                note.noteDate
            )}
                </div>

                <div class="note-item-preview">
                    ${escapeHtml(
                getPreview(
                    note.content
                )
            )}
                </div>

            `;


            item.addEventListener(
                "click",
                async function () {

                    await saveNow();

                    await openNote(
                        note.id
                    );
                }
            );


            list.appendChild(
                item
            );
        }
    );


    const count =
        document.getElementById(
            "notesCount"
        );


    if (count) {

        count.textContent =
            notes.length +
            (
                notes.length === 1
                    ? " note"
                    : " notes"
            );
    }
}


// =====================================================
// OPEN NOTE
// =====================================================

async function openNote(id) {

    try {

        const response =
            await fetch(
                `/api/notes/${id}`,
                {
                    method: "GET",
                    credentials: "same-origin"
                }
            );


        if (!response.ok) {

            throw new Error(
                "Unable to open note"
            );
        }


        const note =
            await response.json();


        selectedNoteId =
            note.id;


        const editor =
            document.getElementById(
                "editor"
            );


        const empty =
            document.getElementById(
                "emptyState"
            );


        if (editor) {

            editor.classList.remove(
                "hidden"
            );
        }


        if (empty) {

            empty.classList.add(
                "hidden"
            );
        }


        const title =
            document.getElementById(
                "noteTitle"
            );


        const content =
            document.getElementById(
                "noteContent"
            );


        if (title) {

            title.value =
                note.title || "";
        }


        if (content) {

            content.value =
                note.content || "";
        }


        setStatus(
            "Saved ✓"
        );


        renderSidebar();

    } catch (error) {

        console.error(
            "Open note error:",
            error
        );
    }
}


// =====================================================
// CREATE NOTE
// =====================================================

async function createNewNote() {

    try {

        await saveNow();


        const response =
            await fetch(
                "/api/notes",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    credentials:
                        "same-origin",

                    body:
                        JSON.stringify({
                            title:
                                "Untitled Note",

                            content:
                                ""
                        })
                }
            );


        if (!response.ok) {

            throw new Error(
                "Unable to create note"
            );
        }


        const note =
            await response.json();


        notes.push(
            note
        );


        sortNotes();


        renderSidebar();


        await openNote(
            note.id
        );


        const title =
            document.getElementById(
                "noteTitle"
            );


        if (title) {

            title.focus();

            title.select();
        }


    } catch (error) {

        console.error(
            "Create note error:",
            error
        );
    }
}


// =====================================================
// START AUTO SAVE
// =====================================================

function startAutoSave() {

    if (!selectedNoteId) {

        return;
    }


    setStatus(
        "Saving..."
    );


    clearTimeout(
        saveTimer
    );


    saveTimer =
        setTimeout(
            function () {

                saveNow();

            },
            800
        );
}


// =====================================================
// SAVE
// =====================================================

async function saveNow() {

    if (!selectedNoteId) {

        return;
    }


    clearTimeout(
        saveTimer
    );


    if (saveInProgress) {

        return;
    }


    const titleElement =
        document.getElementById(
            "noteTitle"
        );


    const contentElement =
        document.getElementById(
            "noteContent"
        );


    if (!titleElement ||
        !contentElement) {

        return;
    }


    const title =
        titleElement.value;


    const content =
        contentElement.value;


    saveInProgress = true;


    try {

        const response =
            await fetch(
                `/api/notes/${selectedNoteId}`,
                {
                    method: "PUT",

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
                            title:
                            title,

                            content:
                            content
                        })
                }
            );


        if (response.status === 401) {

            window.location.href =
                "/login.html";

            return;
        }


        const text =
            await response.text();


        if (!response.ok) {

            console.error(
                "SAVE FAILED",
                response.status,
                text
            );


            setStatus(
                "Save failed ✗"
            );


            return;
        }


        const updated =
            JSON.parse(
                text
            );


        const index =
            notes.findIndex(
                function (note) {

                    return Number(
                            note.id
                        ) ===
                        Number(
                            selectedNoteId
                        );
                }
            );


        if (index !== -1) {

            notes[index] =
                updated;

        } else {

            notes.push(
                updated
            );
        }


        sortNotes();


        renderSidebar();


        setStatus(
            "Saved ✓"
        );


    } catch (error) {

        console.error(
            "SAVE ERROR:",
            error
        );


        setStatus(
            "Save failed ✗"
        );

    } finally {

        saveInProgress = false;
    }
}


// =====================================================
// DELETE
// =====================================================

async function deleteNote() {

    if (!selectedNoteId) {

        return;
    }


    if (
        !confirm(
            "Delete this note?"
        )
    ) {

        return;
    }


    try {

        const response =
            await fetch(
                `/api/notes/${selectedNoteId}`,
                {
                    method: "DELETE",
                    credentials: "same-origin"
                }
            );


        if (!response.ok) {

            throw new Error(
                "Delete failed"
            );
        }


        notes =
            notes.filter(
                function (note) {

                    return Number(
                            note.id
                        ) !==
                        Number(
                            selectedNoteId
                        );
                }
            );


        selectedNoteId =
            null;


        renderSidebar();


        if (notes.length > 0) {

            await openNote(
                notes[0].id
            );

        } else {

            const editor =
                document.getElementById(
                    "editor"
                );


            const empty =
                document.getElementById(
                    "emptyState"
                );


            if (editor) {

                editor.classList.add(
                    "hidden"
                );
            }


            if (empty) {

                empty.classList.remove(
                    "hidden"
                );
            }
        }


    } catch (error) {

        console.error(
            "Delete error:",
            error
        );
    }
}


// =====================================================
// STATUS
// =====================================================

function setStatus(
    text
) {

    const status =
        document.getElementById(
            "saveStatus"
        );


    if (status) {

        status.textContent =
            text;
    }
}


// =====================================================
// DATE
// =====================================================

function formatDate(
    value
) {

    if (!value) {

        return "";
    }


    const date =
        new Date(
            value
        );


    if (
        isNaN(
            date.getTime()
        )
    ) {

        return String(value);
    }


    return date.toLocaleDateString(
        "en-IN",
        {
            day: "2-digit",
            month: "short",
            year: "numeric"
        }
    );
}


// =====================================================
// PREVIEW
// =====================================================

function getPreview(
    content
) {

    if (!content) {

        return "Empty note";
    }


    return String(
        content
    )
        .replace(
            /\n/g,
            " "
        )
        .trim()
        .substring(
            0,
            80
        );
}


// =====================================================
// HTML ESCAPE
// =====================================================

function escapeHtml(
    value
) {

    return String(
        value || ""
    )
        .replace(
            /&/g,
            "&amp;"
        )
        .replace(
            /</g,
            "&lt;"
        )
        .replace(
            />/g,
            "&gt;"
        )
        .replace(
            /"/g,
            "&quot;"
        )
        .replace(
            /'/g,
            "&#039;"
        );
}