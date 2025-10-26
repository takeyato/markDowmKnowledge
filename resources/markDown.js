  const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    height: '500px',
    initialEditType: 'markdown',
    previewStyle: 'vertical',
    placeholder: 'ここにMarkdownを入力してください...',
    toolbarItems: [
      ['heading', 'bold', 'italic', 'strike'],
      ['hr', 'quote'],
      ['ul', 'ol', 'task', 'indent', 'outdent'],
      ['table', 'image', 'link'],
      ['code', 'codeblock']
    ]
  });

  // 例：保存ボタンでMarkdown取得
  document.querySelector('.btn-success').addEventListener('click', () => {
    const markdown = editor.getMarkdown();
    console.log('保存するMarkdown:', markdown);
    // Ajaxで送信など
  });
  
editor.addHook('addImageBlobHook', async (blob, callback) => {
  const imageEditor = new tui.ImageEditor(document.querySelector('#image-editor'), {
    includeUI: {
      loadImage: { path: URL.createObjectURL(blob), name: 'uploaded' },
      theme: {}, // カスタムテーマ
      menu: ['crop', 'flip', 'rotate'],
      initMenu: 'crop',
      uiSize: { width: '1000px', height: '700px' },
      menuBarPosition: 'bottom'
    },
    cssMaxWidth: 700,
    cssMaxHeight: 500
  });

  // 編集完了後に画像を取得して軽量化
  document.querySelector('#confirm-button').addEventListener('click', () => {
    const editedCanvas = imageEditor.toDataURL(); // Base64形式
    const compressed = compressImage(editedCanvas); // 軽量化関数（下記参照）
    callback(compressed, 'edited.png');
    imageEditor.destroy();
  });
});
function compressImage(dataUrl, quality = 0.7) {
  const img = new Image();
  img.src = dataUrl;
  return new Promise(resolve => {
    img.onload = () => {
      const canvas = document.createElement('canvas');
      canvas.width = img.width;
      canvas.height = img.height;
      const ctx = canvas.getContext('2d');
      ctx.drawImage(img, 0, 0);
      const compressed = canvas.toDataURL('image/jpeg', quality);
      resolve(compressed);
    };
  });
}
