const searchInput = document.getElementById('searchInput');
const items = document.getElementsByClassName('item');
const removeButtons = document.getElementsByClassName('btn-remove');

searchInput.addEventListener('input', function () {
  const searchValue = this.value.toLowerCase();

  for (let i = 0; i < items.length; i++) {
    const item = items[i];
    const itemName = item.querySelector('.item-name').textContent.toLowerCase();

    if (itemName.includes(searchValue)) {
      item.style.display = 'block';
    } else {
      item.style.display = 'none';
    }
  }
});

for (let i = 0; i < removeButtons.length; i++) {
  const removeButton = removeButtons[i];
  removeButton.addEventListener('click', function () {
    const item = this.parentNode;
    item.parentNode.removeChild(item);
  });
}