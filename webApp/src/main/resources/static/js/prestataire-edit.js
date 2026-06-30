// Ce fichier suppose que `professions` et `adresses` sont déjà déclarés
// globalement par le <script th:inline="javascript"> de la page HTML.
// Ne PAS redéclarer ces variables ici (sinon SyntaxError: redeclaration).

function addProfession() {
    const container = document.getElementById('profession-container');
    const div = document.createElement('div');
    div.className = 'mb-2';

    let options = '<option value="">-- Choisir --</option>';
    professions.forEach(p => {
        options += `<option value="${p.id}">${p.nom}</option>`;
    });

    div.innerHTML = `
        <select name="professionIds" class="ContentBox2-detailRdv-input">
            ${options}
        </select>
        <button type="button" class="btn btn-danger btn-sm mt-1"
                onclick="this.parentElement.remove()">Supprimer</button>
    `;
    container.appendChild(div);
}

function addAdresse() {
    const container = document.getElementById('adresse-container');
    const div = document.createElement('div');
    div.className = 'mb-2 adresse-block';

    let options = '<option value="">-- Nouvelle adresse --</option>';
    adresses.forEach(a => {
        options += `<option value="${a.id}">${a.numero || ''} ${a.rue || ''}, ${a.ville || ''}</option>`;
    });

    div.innerHTML = `
        <select name="adresseIds" class="ContentBox2-detailRdv-input mb-1"
                onchange="toggleNewAdresse(this)">
            ${options}
        </select>
        <div class="new-adresse-fields" style="display:none;">
            <input type="text" name="rues" class="ContentBox2-detailRdv-input" placeholder="Rue"/>
            <input type="text" name="numeros" class="ContentBox2-detailRdv-input" placeholder="Numéro"/>
            <input type="text" name="villes" class="ContentBox2-detailRdv-input" placeholder="Ville"/>
            <input type="text" name="codePostals" class="ContentBox2-detailRdv-input" placeholder="Code postal"/>
        </div>
        <button type="button" class="btn btn-danger btn-sm mt-1"
                onclick="this.parentElement.remove()">Supprimer</button>
    `;
    container.appendChild(div);

    // Le nouveau bloc est sur "-- Nouvelle adresse --" par défaut :
    // on affiche directement les champs de saisie sans attendre un onchange.
    const newSelect = div.querySelector('select[name="adresseIds"]');
    toggleNewAdresse(newSelect);
}

// Afficher/masquer les champs nouvelle adresse
function toggleNewAdresse(select) {
    const fields = select.nextElementSibling;
    if (select.value === '') {
        fields.style.display = 'block';
    } else {
        fields.style.display = 'none';
        fields.querySelectorAll('input').forEach(i => i.value = '');
    }
}

// Init au chargement — masquer les champs si adresse déjà sélectionnée
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('select[name="adresseIds"]').forEach(select => {
        toggleNewAdresse(select);
    });
});