package com.example.musicapp

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var player: MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.song)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init(currentSong[0])
        initAudioFocusChangeListener()
    }

    // initiates Audio Focus Change Listener through which we can focus on audio change
    // we pause the player when focus changes
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initAudioFocusChangeListener() {
        val audioFocusChangeListener =
            AudioManager.OnAudioFocusChangeListener { focusChange ->

                if (focusChange <= 0) {
                    //pause music
                    player?.pause()
                }
            }

        val audioManger = getSystemService(AUDIO_SERVICE) as AudioManager

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(audioAttributes)
            .setAcceptsDelayedFocusGain(true)
            .setOnAudioFocusChangeListener(audioFocusChangeListener)
            .build()

        val result = audioManger.requestAudioFocus(focusRequest)

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            player?.start()
        }
    }

    // initiates music player with play, pause and stop buttons
    // As name suggests, the buttons do the same particular tasks
    private fun init(song: Int) {

        binding.apply {

            fabPlay.setOnClickListener {

                if (player == null) {
                    player = MediaPlayer.create(this@MainActivity, song)
                }
                player?.start()


            }

            fabPause.setOnClickListener {

                if (player != null) player?.pause()
            }

            fabStop.setOnClickListener {
                if (player != null) {
                    player?.apply {
                        stop()
                        reset()
                        release()
                        player = null
                    }
                }
            }

        }

    }

    // when app goes to background
    /* player gets paused and then the last played position of the song
    is sent to background service to continue the task  */
    override fun onPause() {
        super.onPause()
        if (player?.isPlaying == true) {
            player?.pause()
            val seekTo = player?.currentPosition ?: 0
            val intent = Intent(this, PlayerService::class.java)
            intent.putExtra("seekTo", seekTo)
            startService(intent)
        }
    }

    // when app gets killed
    // the player and  background service will be stopped
    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, PlayerService::class.java))
    }
}

