package com.guerra.enrico.sera.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.guerra.enrico.sera.BuildConfig
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.data.result.succeeded
import com.guerra.enrico.sera.ui.base.BaseActivity
import com.guerra.enrico.sera.ui.todos.TodosActivity
import com.guerra.enrico.sera.util.viewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/10/2018.
 */
const val REQUEST_CODE_SIGNIN = 9003

class LoginActivity: BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = viewModelProvider(viewModelFactory)
        initView()
    }

    override fun initView() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(BuildConfig.OAUTH2_CLIENT_ID)
                .build()
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        signInButton.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, REQUEST_CODE_SIGNIN)
        }
        viewModel.observeUserSignIn().observe(this, Observer {
            userResult ->
            if (userResult == null || isFinishing) return@Observer
            if (userResult == Result.Loading) {
                return@Observer
            }
            if (userResult.succeeded) {
                gotoTodosActivity()
            }
            if (userResult is Result.Error) {
                showSnakbar(userResult.exception.message ?: resources.getString(R.string.error_google_signin))
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGNIN) {
            handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            viewModel.onCodeReceived(account?.serverAuthCode ?: "")
        } catch (ex: ApiException) {
            showSnakbar(R.string.error_google_signin)
        }
    }

    private fun gotoTodosActivity() {
        startActivity(Intent(this, TodosActivity::class.java))
        finish()
    }
}