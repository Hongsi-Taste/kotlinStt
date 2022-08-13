package com.gachon.stt_ktlin

import android.os.Build
import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(Build.VERSION.SDK_INT>=23){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.RECORD_AUDIO),0)
        }

        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")

        setListener()

        recBtn.setOnClickListener {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            speechRecognizer.setRecognitionListener(recognitionListener)
            speechRecognizer.startListening(intent)
        }
    }

    private fun setListener(){
        recognitionListener = object: RecognitionListener{
            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(applicationContext,"Recording start", Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {
                TODO("Not yet implemented")
            }

            override fun onRmsChanged(rmsdB: Float) {
                TODO("Not yet implemented")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                TODO("Not yet implemented")
            }

            override fun onEndOfSpeech() {
                TODO("Not yet implemented")
            }

            override fun onError(error: Int) {
                var message: String
                when(error){
                    SpeechRecognizer.ERROR_AUDIO ->
                        message = "Audio Error"
                    SpeechRecognizer.ERROR_CLIENT ->
                        message = "Client Error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS ->
                        message = "No permissions"
                    SpeechRecognizer.ERROR_NETWORK ->
                        message = "Network Error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT ->
                        message = "Network TIMEOUT"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY ->
                        message = "Recognizer is busy"
                    SpeechRecognizer.ERROR_SERVER ->
                        message = "SERVER is weird"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                        message = "Speech Time Exceeded"
                    else ->
                        message = "Unknown Error"
                }
                Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                var matches: ArrayList<String>? = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                if (matches != null) {
                    for(i in 0 until matches.size){
                        resView.text = matches[i]
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                TODO("Not yet implemented")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                TODO("Not yet implemented")
            }

        }
    }
}