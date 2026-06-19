function toggleAdminMenu() {
    const sidebar = document.getElementById('adminSidebar');
    const overlay = document.getElementById('adminOverlay');
    const icon = document.getElementById('burgerIcon');

    sidebar.classList.toggle('open');
    overlay.classList.toggle('active');

    if (sidebar.classList.contains('open')) {
        icon.classList.remove('fa-bars');
        icon.classList.add('fa-xmark');
    } else {
        icon.classList.remove('fa-xmark');
        icon.classList.add('fa-bars');
    }
}

function closeAdminMenu() {
    document.getElementById('adminSidebar').classList.remove('open');
    document.getElementById('adminOverlay').classList.remove('active');
    const icon = document.getElementById('burgerIcon');
    icon.classList.remove('fa-xmark');
    icon.classList.add('fa-bars');
}