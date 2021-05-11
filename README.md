# Android-Sivar
El equipo de Android Sivar tiene interés en crear una aplicación para sus seguidores, esta aplicación será un proyecto nativo en Android que mostrará los artículos de noticias más recientes que contengan a Android como parte de su contenido y/o título, adicionalmente Android Sivar quiere que la aplicación permite a los usuarios crear sus propios artículos localmente

#Procesos
En esta App se encontrara una pantalla principal donde se desplagara un recyclerVIew donde se obtendran los resultados de la API sobre las noticias en el orden de popularidad, se econtraran los botones de anterior y siguiente para poder navegar en los resultados d e la llamada a la API,  tambien en la parte superior existe un boton para poder cambiar el idioma y poder obtener la información en el idioma que se prefiera.

En el lado izquierda al hacer movimiento desde izquierda  a derecha se desplegara un menu donde se encontrara la opcion de "mis publicaciones" e "inicio".

En la opcion de mis publicaciones se encontrara el espacio para que el usuario pueda crear sus propios articulos sobre Android y de igual manera puede editarlos y borrarlos si asi desea.

#Dependencias utilizadas
 //dependencias para navigation drawer
    implementation 'com.google.android.material:material:1.2.1'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'com.intuit.sdp:sdp-android:1.0.6'
    //dependencias para recycler view
    implementation 'androidx.cardview:cardview:1.0.0' 
    implementation "androidx.recyclerview:recyclerview:1.2.0"
    //retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    //glide
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    //room
    implementation 'androidx.room:room-ktx:2.3.0'
    //lifecycle
    kapt 'androidx.room:room-compiler:2.3.0'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.2.0'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    
    
    
