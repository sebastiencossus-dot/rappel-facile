const navBox = document.querySelector('.NavBox');
const burger = document.querySelector('.NavBox-burger');
const closeBtn = document.querySelector('.NavBox-close');

[burger, closeBtn].forEach(btn => {
    btn.addEventListener('click', () => {
        navBox.classList.toggle('active');
    });
});