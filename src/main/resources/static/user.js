const URLNavbarUser = 'http://localhost:8080/api/user/showAccount/';
const navbarUser = document.getElementById('navbarUser');
const tableUserUser = document.getElementById('tableUser');

function getCurrentUser() {
    fetch(URLNavbarUser)
        .then((res) => res.json())
        .then((user) => {

            let rolesStringUser = rolesToStringForUser(user.roles);
            let dataOfUser = '';

            dataOfUser += `<tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${rolesStringUser}</td>
            </tr>`;

            tableUserUser.innerHTML = dataOfUser;
            navbarUser.innerHTML = `<b><span>${user.firstName}</span></b>
                             <span>with roles:</span>
                             <span>${rolesStringUser}</span>`;
        });
}

getCurrentUser()

function rolesToStringForUser(roles) {
    if (!Array.isArray(roles)) {
        console.error('roles is not an array:', roles);
        return 'Invalid roles data';
    }

    let rolesString = '';
    for (let element of roles) {
        if (!element.name) {
            console.error('nameOfRole is missing in roles element:', element);
            continue;
        }
        rolesString += (element.name.toString().replace('ROLE_', '') + ', ');
    }
    rolesString = rolesString.substring(0, rolesString.length - 2);
    return rolesString;
}