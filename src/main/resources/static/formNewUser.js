let formNewUser = document.forms["formNewUser"];

createNewUser();

function createNewUser() {
    formNewUser.addEventListener("submit", ev => {
        ev.preventDefault();

        let rolesForNewUser = [];
        for (let i = 0; i < formNewUser.roles.options.length; i++) {
            if (formNewUser.roles.options[i].selected)
                rolesForNewUser.push({
                    id: formNewUser.roles.options[i].value,
                    role: "ROLE_" + formNewUser.roles.options[i].text
                });
        }

        fetch("http://localhost:8080/api/admin/users/", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName: formNewUser.firstName.value,
                lastName: formNewUser.lastName.value,
                password: formNewUser.password.value,
                roles: rolesForNewUser
            })
        }).then(response => {
            if (!response.ok) {
                return response.json();
            } else {
                return { message: "User updated successfully" };
            }
        }).then(data => {
            if (Array.isArray(data) && data.length > 0 && data[0].defaultMessage) {
                const validationErrorsElement = document.getElementById("validationErrorsNewUser");
                validationErrorsElement.innerHTML = "";
                data.forEach(error => {
                    validationErrorsElement.innerHTML += `<p>${error.defaultMessage}</p>`;
                });
            } else {
                const validationErrorsElement = document.getElementById("validationErrorsNewUser");
                validationErrorsElement.innerHTML = "";
                $('#usersTable').click();
                getAllUsers();
            }
        })
    });
}

function loadRolesForNewUser() {
    let selectAdd = document.getElementById("create-roles");

    selectAdd.innerHTML = "";

    fetch("http://localhost:8080/api/admin/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.name.replace('ROLE_', '')
                selectAdd.appendChild(option);
            });
        })
}

window.addEventListener("load", loadRolesForNewUser);