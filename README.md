# QuizApp

### 🎯 Uygulamanın Amacı
JSON dosyasındaki soruları kullanıcıya yönelterek bir quiz deneyimi sunmak.

## ⚙️ Uygulama Çalışma Mantığı

- 📌 Her soruda bir şık işaretlenmeden diğer soruya geçiş yapılamaz.
- ⏳ Belirtilen süre içinde bir şık işaretlenmezse, soru **boş sayılır** ve sonraki soruya geçilir.
- 🕒 Quiz için tanımlanan süre içerisinde sınav tamamlanamazsa, **işaretlenmemiş sorular boş sayılır** ve quiz sona erer.
- 🚫 Hiçbir şekilde geriye dönüş yapılamaz; önceki sorulara erişim mümkün değildir.
- 📋 Sınav sona erdiğinde, **puanınız ve adınız sonuç sayfasında** görüntülenir.
