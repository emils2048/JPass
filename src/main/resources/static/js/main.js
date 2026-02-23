document.addEventListener('DOMContentLoaded', function(){
  // confirm data-confirm
  document.body.addEventListener('click', function(e){
    const t = e.target;
    if(t.matches && t.matches('[data-confirm]')){
      const msg = t.getAttribute('data-confirm') || '정말 진행하시겠습니까?';
      if(!confirm(msg)) e.preventDefault();
    }
  });
});