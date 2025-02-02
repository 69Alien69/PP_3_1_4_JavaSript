const addForm = document.getElementById("addForm");
const editForm = document.getElementById("form-edit");
const deleteForm = document.getElementById("form-delete");

async function getRoles() {
    const response = await fetch("/api/admin/get_roles", {method: "GET"});
    if (response.ok) {
        const roles = await response.json();
        let rolesList = [];
        roles.forEach(role => rolesList.push(role.roleName));
        return rolesList;
    }
}
async function getUsers() {
    const response = await fetch("/api/admin/users_table", {method: "GET"});
    if (response.ok) {
        const users = await response.json();
        const rows = document.getElementById("users_table");
        while (rows.lastElementChild != null) {
            rows.removeChild(rows.lastElementChild);
        }
        users.forEach(user => rows.append(row(user)));
        await setNavBarCurrentUser();
        await setCurrentUserTable();
    }
}


async function getCurrentUser() {
    const response = await fetch("/api/admin/user", {method: "GET"})
    if (response.ok === true) {
        return await response.json();
    }
}

async function addUser(firstName, lastName, age, email, password, rolesPicked) {
    const response = await fetch("/api/admin/add", {
        method: "POST",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: JSON.stringify({
            firstName: firstName,
            lastName: lastName,
            age: parseInt(age, 10),
            email: email,
            password: password,
            role: rolesPicked
        })
    });
    if (response.ok) {
        reset(addForm);
        document.getElementById("table-tab").click();
    }
    getUsers();
}
async function editUser(id, firstName, lastName, age, email, password, rolesPicked) {
    const response = await fetch("/api/admin/edit", {
        method: "PUT",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: JSON.stringify({
            id: id,
            firstName: firstName,
            lastName: lastName,
            age: parseInt(age, 10),
            email: email,
            password: password,
            role: rolesPicked
        })
    });
    if (response.ok) {
        reset(editForm);
        document.getElementById("table-tab").click();
    }
    getUsers();
}

async function deleteUser(id) {
    const response = await fetch("/api/admin/delete", {
        method: "DELETE",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: JSON.stringify({
            id: id
        })
    });
    if (response.ok) {
        reset(deleteForm);
        document.getElementById("table-tab").click();
    }
    getUsers();
}

function reset(form) {
    form.reset();
}

async function setNavBarCurrentUser() {
    const p = document.getElementById("p-current-user");

    while (p.lastElementChild) {
        p.removeChild(p.lastElementChild);
    }

    const user = await getCurrentUser();
    const emailSpan = document.createElement("span");
    emailSpan.append(user.email);
    emailSpan.setAttribute("class", "fw-bold")

    const textSpan = document.createElement("span");
    textSpan.append(" with roles: ");

    const rolesSpan = document.createElement("span")
    user.roles.forEach(role => rolesSpan.append(role + " "));

    p.append(emailSpan);
    p.append(textSpan);
    p.append(rolesSpan);
}

async function setCurrentUserTable() {
    const user = await getCurrentUser();
    const table = document.getElementById("current-user-table")

    while (table.lastElementChild) {
        table.removeChild(table.lastElementChild)
    }

    const tr = document.createElement("tr");
    tr.setAttribute("data-rowid", user.id);

    const idTd = document.createElement("td");
    idTd.append(user.id);
    tr.append(idTd);

    const firstNameTd = document.createElement("td");
    firstNameTd.append(user.firstName);
    tr.append(firstNameTd);

    const lastNameTd = document.createElement("td");
    lastNameTd.append(user.lastName);
    tr.append(lastNameTd);

    const ageTd = document.createElement("td");
    ageTd.append(user.age);
    tr.append(ageTd);

    const emailTd = document.createElement("td");
    emailTd.append(user.email);
    tr.append(emailTd);

    const roleTd = document.createElement("td");
    user.roles.forEach(role => roleTd.append(role + " "));
    tr.append(roleTd);

    table.append(tr);
}

function row(user) {

    const tr = document.createElement("tr");
    tr.setAttribute("data-rowid", user.id);

    const idTd = document.createElement("td");
    idTd.append(user.id);
    tr.append(idTd);

    const firstNameTd = document.createElement("td");
    firstNameTd.append(user.firstName);
    tr.append(firstNameTd);

    const lastNameTd = document.createElement("td");
    lastNameTd.append(user.lastName);
    tr.append(lastNameTd);

    const ageTd = document.createElement("td");
    ageTd.append(user.age);
    tr.append(ageTd);

    const emailTd = document.createElement("td");
    emailTd.append(user.email);
    tr.append(emailTd);

    const roleTd = document.createElement("td");
    user.roles.forEach(role => roleTd.append(role + " "));
    tr.append(roleTd);

    const editLinkTd = document.createElement("td");

    const editLink = document.createElement("button");
    editLink.setAttribute("class", "btn btn-info text-white");
    editLink.setAttribute("data-bs-toggle", "modal");
    editLink.setAttribute("data-bs-target", "#editModal");
    editLink.setAttribute("data-id", user.id);
    editLink.append("Edit");
    editLinkTd.append(editLink);
    tr.append(editLinkTd);

    editLink.addEventListener("click", e => {
        createEditModal(user);
    })

    const removeLinkTd = document.createElement("td");

    const removeLink = document.createElement("button");
    removeLink.setAttribute("class", "btn btn-danger");
    removeLink.setAttribute("data-bs-toggle", "modal");
    removeLink.setAttribute("data-bs-target", "#deleteModal");
    removeLink.setAttribute("data-id", user.id);
    removeLink.append("Delete");
    removeLinkTd.append(removeLink);
    tr.append(removeLinkTd);

    removeLink.addEventListener("click", e => {
        createDeleteModal(user);
    })

    return tr;
}

addForm.addEventListener("submit", e => {
    e.preventDefault();
    const firstName = addForm.elements["firstName"].value;
    const lastName = addForm.elements["lastName"].value;
    const age = addForm.elements["age"].value;
    const email = addForm.elements["email"].value;
    const password = addForm.elements["password"].value;
    let rolesPicked = '';
    const options = addForm.elements["rolesPicked"].selectedOptions;
    for (let i = 0; i < options.length; i++) {
        rolesPicked = rolesPicked.concat(options.item(i).text);
    }
    addUser(firstName, lastName, age, email, password, rolesPicked);
});

editForm.addEventListener("submit", e => {
    e.preventDefault();
    const id = editForm.elements["id"].value;
    const firstName = editForm.elements["firstName"].value;
    const lastName = editForm.elements["lastName"].value;
    const age = editForm.elements["age"].value;
    const email = editForm.elements["email"].value;
    const password = editForm.elements["password"].value;
    let rolesPicked = '';
    const options = editForm.elements["rolesPicked"].selectedOptions;
    for (let i = 0; i < options.length; i++) {
        rolesPicked = rolesPicked.concat(options.item(i).text);
    }
    editUser(id, firstName, lastName, age, email, password, rolesPicked);
    document.getElementById("edit-modal-close").click();
});

deleteForm.addEventListener("submit", e => {
    e.preventDefault();
    deleteUser(document.getElementById("idDelete").getAttribute("value"));
    document.getElementById("delete-modal-close").click();
});

async function createEditModal(user) {
    const roleSelect = document.getElementById("roleEdit");
    const rolesList = await getRoles();
    document.getElementById("idEdit").setAttribute("value", user.id);
    document.getElementById("idEditHidden").setAttribute("value", user.id);
    document.getElementById("firstNameEdit").setAttribute("value", user.firstName);
    document.getElementById("lastNameEdit").setAttribute("value", user.lastName);
    document.getElementById("ageEdit").setAttribute("value", user.age);
    document.getElementById("emailEdit").setAttribute("value", user.email);
    while (roleSelect.lastElementChild) {
        roleSelect.removeChild(roleSelect.lastElementChild)
    }
    rolesList.forEach(role => roleSelect.options[roleSelect.options.length] = new Option(role, role, false, user.roles.includes(role)));
    document.getElementById("roleEdit").setAttribute("value", user.role);
}

async function createDeleteModal(user) {
    const roleSelect = document.getElementById("roleDelete");
    const rolesList = await getRoles();
    document.getElementById("idDelete").setAttribute("value", user.id);
    document.getElementById("idDeleteHidden").setAttribute("value", user.id);
    document.getElementById("firstNameDelete").setAttribute("value", user.firstName);
    document.getElementById("lastNameDelete").setAttribute("value", user.lastName);
    document.getElementById("ageDelete").setAttribute("value", user.age);
    document.getElementById("emailDelete").setAttribute("value", user.email);
    while (roleSelect.lastElementChild) {
        roleSelect.removeChild(roleSelect.lastElementChild)
    }
    rolesList.forEach(role => roleSelect.options[roleSelect.options.length] = new Option(role, role, false, user.roles.includes(role)));
    document.getElementById("roleDelete").setAttribute("value", user.role);
}

getUsers();