async function getCurrentUser() {
    const response = await fetch("/api/user/user", {method: "GET"})
    if (response.ok === true) {
        return await response.json();
    }
}

async function setNavBarUser() {
    const p = document.getElementById("p-current-user");

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

async function setUserTable() {
    const user = await getCurrentUser();
    const table = document.getElementById("user-table")

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

setUserTable();
setNavBarUser();