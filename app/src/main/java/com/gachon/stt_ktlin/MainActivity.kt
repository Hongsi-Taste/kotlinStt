package com.gachon.stt_ktlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var speechRecognizer : SpeechRecognizer
    private lateinit var recogListener : RecognitionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")

        setListener()

        recBtn.setOnClickListener {
            speechRecognizer = speechRecognizer.createSpeechRecognizer(this)
            speechRecognizer.setRecognitionListener(recogListener)
            speechRecognizer.startListening(intent)
        }

    }
    //권한 문의 함수
    private fun requestPermission(){
        if(Build.VERSION.SDK_INT>=23 && ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

            //거부해도 계속 노출됨.("다시 묻지않음" 체크시에는 노출 안됨.)
            //한번 허용되면 이후에는 자동으로 허용됨.
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.RECORD_AUDIO
            ),0)
        }
    }
    //RecognitionListener 확장
    private fun setListener(){
        recogListener = object : RecognitionListener{

            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(applicationContext, "Recordingstart", Toast.LENGTH_SHORT).show()
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
                        message = "Permissions not granted"
                    SpeechRecognizer.ERROR_NETWORK ->
                        message = "Network Error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT ->
                        message = "Network Timeout"
                    SpeechRecognizer.ERROR_NO_MATCH ->
                        message = "Cannot match"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY ->
                        message = "Recognizer is busy?"
                    SpeechRecognizer.ERROR_SERVER ->
                        message = "SERVER Error"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                        message = "Recording timeout"
                    else ->
                        message = "UNKNOWN ERROR"
                }
                Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                var matches: ArrayList<String> = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>

                for(i in 0 until matches.size){
                    resView.text = matches[i]
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