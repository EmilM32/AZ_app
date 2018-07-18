# AZ_app
This app <b>get data from Github and Bitbucket API</b> and shows it in ListView (user name, repository name and avatar). <br>
You can also <b>click on ListView element and go to second activity</b> which is "personal page" for every user (with extra repository description from api).<br> 
Every element on the list which is from <b>Bitbucket API is hightlighted</b> (you can see it on screenshots).<br>
<b>Sorting</b>: you can sort list by repository name - use checkbox on MainActivity<br><br>
<b>App can check if there is any Internet connection</b>, if not you'll see dialog with information about this.<br>

## Lilibraries:
<ul>
  <li>Picasso</li>
</ul>
  
  Picasso is very easy to use and simple library to download images. To use it you have to add this line to your Gradle (build.gradle Module:app) in dependencies section: <br>
   `implementation 'com.squareup.picasso:picasso:2.71828'` <br>
  
   Then usage looks like this:<br>
   `Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);`<br><br>
   I use this library to download avatars from APIs.<br>
   
   For more information about Picasso click [HERE](http://square.github.io/picasso/)<br><br>
   
## Screenshots
  <img src="Screenshot_1.png" width="200px"><img src="Screenshot_2.png" width="200px"><img src="Screenshot_3.png" width="200px"><img src="Screenshot_4.png" width="200px">
  <br><br>
