<!-- wp:paragraph -->
<p>Create custom image classification models from your own training data with AutoML Vision Edge.</p>
<!-- /wp:paragraph -->

<!-- wp:paragraph -->
<p>ML Kit's base on-device image labelling API model is built for general-purpose use, and is trained to recognize around 400 categories that cover the most commonly-found concepts in photos. If you need a more specialized image labelling model, covering a narrower domain of concepts in more detail—for example, a model to distinguish between species of flowers or types of food—you can use AutoML Vision Edge to train a model with your own images and use the model you trained instead.</p>
<!-- /wp:paragraph -->
<!-- wp:heading {"level":3} -->
<h3 id="train-an-image-labeling-model-with-automl-vision-edge"><strong>Train an image labelling model with AutoML</strong></h3>
<!-- /wp:heading -->

<!-- wp:paragraph -->
<p>To train an image labelling model, you provide AutoML Vision Edge with a set of images and corresponding labels. AutoML Vision Edge uses this dataset to train a new model in the cloud, which you can use for on-device image labelling in your app. <br>You have to follow below steps for training of image labelling model:</p>
<!-- /wp:paragraph -->

<!-- wp:list -->
<ul><li>Login to your <a rel="noreferrer noopener" aria-label="firebase console (opens in a new tab)" href="https://console.firebase.google.com" target="_blank">Firebase console</a></li><li>Add a Firebase project </li><li>Inside your project, Goto<br> Develop --> ML Kit --> Get Started --> AutoML</li><li>In AutoML, Add data-set by giving the data-set name</li><li>Inside the data-set, import the data using zip file.<br> ZIP has a specific format, for an example</li></ul>
<!-- /wp:list -->

<!-- wp:image {"align":"center","id":3202} -->
<div class="wp-block-image"><figure class="aligncenter"><img src="https://androidcreativity.files.wordpress.com/2019/09/sample.png?w=229" alt="" class="wp-image-3202"/></figure></div>
<!-- /wp:image -->

<!-- wp:list -->
<ul><li>In above zip, you have to put at-least 10 images for specific category. If you put more images for each category then you will get better result. </li><li>It will take time to import the data using ZIP. Firebase will send you the email once importing is done so no need to wait or stay on the platform.</li><li>Once importing is done, check the images for proper labelling</li><li>In next step, Put your model for training.<br>- If you are using free account then you can only put the model for training of maximum 1 hour.</li><li>It will take time to train the model. Firebase will send you the email once training is done so no need to wait or stay on the platform.</li><li>Once training done, Goto<br>    Your data-set  -->  Trained Model  -->  Use Model  --> Publish </li><li>After publishing the model, download the model as well for the offline uses.</li></ul>
<!-- /wp:list -->

<!-- wp:separator -->
<hr class="wp-block-separator"/>
<!-- /wp:separator -->

<!-- wp:paragraph -->
<p><strong>Note:</strong> <strong>Before start integrating, enable cloud Vision API in Google APIs console.</strong><br><a href="https://console.developers.google.com/">https://console.developers.google.com</a> --> Click 'Enable APIs and Services'      --> Search and click 'Cloud Vision API'  --> Enable API</p>
<!-- /wp:paragraph -->

<!-- wp:separator -->
<hr class="wp-block-separator"/>
<!-- /wp:separator -->

<!-- wp:heading {"level":3} -->
<h3><strong>Label images with an AutoML-trained model on Android</strong></h3>
<!-- /wp:heading -->

<!-- wp:paragraph -->
<p>- Create a new Android project and follow below steps:<br>- If you haven't already, <a href="https://firebase.google.com/docs/android/setup">add Firebase to your Android project</a>.<br>- Inside project/build.gradle</p>
<!-- /wp:paragraph -->

<!-- wp:preformatted -->
<pre class="wp-block-preformatted">buildscript {
    
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath <strong>'com.android.tools.build:gradle:3.4.1'
        </strong>classpath <strong>'com.google.gms:google-services:4.3.2'</strong><em>
    </em>}
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}</pre>
<!-- /wp:preformatted -->

<!-- wp:paragraph -->
<p>- Add below dependencies in app/build.gradle</p>
<!-- /wp:paragraph -->

<!-- wp:preformatted -->
<pre class="wp-block-preformatted">dependencies {<br>&nbsp; // ...<br><br>&nbsp; implementation 'com.google.firebase:firebase-ml-vision:23.0.0'<br>&nbsp; implementation 'com.google.firebase:firebase-ml-vision-automl:18.0.1'<br>}<br>apply plugin: 'com.google.gms.google-services'</pre>
<!-- /wp:preformatted -->

<!-- wp:paragraph -->
<p>- Create a assets folder inside app/src/main using below steps:<br>  Right click on app module -> New ->  Folder -> Assets Folder -> Select target 'main' then Submit<br>- Copy download model files (<code>model.tflite</code>, <code>dict.txt</code>, and <code>manifest.json</code>) from Firebase AutoML to assets folder.<br>- After that put aapt options in app/build.gradle</p>
<!-- /wp:paragraph -->

<!-- wp:preformatted -->
<pre class="wp-block-preformatted">android {
    // ...
    aaptOptions {
        noCompress "tflite"
    }
}</pre>
<!-- /wp:preformatted -->

<!-- wp:paragraph -->
<p><strong>For the code Please follow below link:</strong></p>
<!-- /wp:paragraph -->

<!-- wp:paragraph -->
<p>https://github.com/arpit0303/AutoML-ImageLabel</p>
<!-- /wp:paragraph -->

<!-- wp:separator -->
<hr class="wp-block-separator"/>
<!-- /wp:separator -->

<!-- wp:paragraph -->
<p><strong>Note: </strong>Before start testing, Put SHA1 Key in your firebase project.<br>     Goto your Firebase project -> Click on Settings icon -> Select Project           settings -> Add SHA cretificate fingerprints<br><br>Generate SHA1 key using below command:</p>
<!-- /wp:paragraph -->

<!-- wp:preformatted -->
<pre class="wp-block-preformatted">keytool -list -v \
-alias YOUR_KEY_NAME -keystore YOUR_KEYSTORE_PATH</pre>
<!-- /wp:preformatted -->
<!-- wp:separator -->
<hr class="wp-block-separator"/>
<!-- /wp:separator -->

<!-- wp:paragraph -->
<p><strong>References:</strong><br><a href="https://firebase.google.com/docs/ml-kit/automl-image-labeling">https://firebase.google.com/docs/ml-kit/automl-image-labeling</a><br><a href="https://firebase.google.com/docs/ml-kit/android/label-images-with-automl">https://firebase.google.com/docs/ml-kit/android/label-images-with-automl</a><br><a href="https://www.youtube.com/watch?v=QwHD36bhXZA">https://www.youtube.com/watch?v=QwHD36bhXZA</a></p>
<!-- /wp:paragraph -->