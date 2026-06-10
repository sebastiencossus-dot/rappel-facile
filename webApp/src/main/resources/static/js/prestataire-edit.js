function addProfession() {
    const container = document.getElementById("profession-container");

    const div = document.createElement("div");
    div.classList.add("item");

    const template = document.querySelector("select[name='professionIds']").innerHTML;

    div.innerHTML = `
        <select name="professionIds">
            ${template}
        </select>
        <button type="button" onclick="this.parentElement.remove()">Supprimer</button>
    `;

    container.appendChild(div);
}

function addAdresse() {
    const container = document.getElementById("adresse-container");

    const div = document.createElement("div");
    div.classList.add("item");

    div.innerHTML = `
        <input type="text" name="rues" placeholder="Rue"/>
        <input type="text" name="numeros" placeholder="Numéro"/>
        <input type="text" name="villes" placeholder="Ville"/>
        <input type="text" name="codePostals" placeholder="Code postal"/>
        <button type="button" onclick="this.parentElement.remove()">Supprimer</button>
    `;

    container.appendChild(div);
}