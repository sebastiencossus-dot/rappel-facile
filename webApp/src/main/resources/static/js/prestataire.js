function addProfession() {
    const container = document.getElementById("profession-container");

    const div = document.createElement("div");
    div.classList.add("profession-item");

    div.innerHTML = `
        <select name="professionIds">
            <option value="">-- Choisir une profession --</option>
            ${document.querySelector('.profession-item select').innerHTML}
        </select>
        <button type="button" onclick="this.parentElement.remove()">Supprimer</button>
    `;

    container.appendChild(div);
}

function addAdresse() {
    const container = document.getElementById("adresse-container");

    const div = document.createElement("div");
    div.classList.add("adresse-item");

    div.innerHTML = `
        <input type="text" name="rues" placeholder="Rue"/>
        <input type="text" name="numeros" placeholder="Numéro"/>
        <input type="text" name="villes" placeholder="Ville"/>
        <input type="text" name="codePostals" placeholder="Code postal"/>
        <button type="button" onclick="this.parentElement.remove()">Supprimer</button>
    `;

    container.appendChild(div);
}

function toggleNouvelleAdresse() {
    const select = document.getElementById('adresse-select');
    const div = document.getElementById('nouvelle-adresse');
    const inputs = div.querySelectorAll('input');

    const isNouvelle = select.value === '';
    div.style.display = isNouvelle ? 'block' : 'none';

    // ← désactive les champs si cachés, ils ne seront pas soumis
    inputs.forEach(input => input.disabled = !isNouvelle);
}


document.addEventListener('DOMContentLoaded', function () {
    toggleNouvelleAdresse();
});