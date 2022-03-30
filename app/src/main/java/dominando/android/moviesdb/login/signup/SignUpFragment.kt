package dominando.android.moviesdb.login.signup

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dominando.android.moviesdb.MainActivity
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentSignUpBinding
import dominando.android.moviesdb.utils.firebase.firebase.saveUser
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment : Fragment() {

    private val navigation get() = findNavController()
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private val viewModel: SignUpViewModel by viewModel()
    private var facebookHasCall = false
    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
        observeLoginResult()
    }

    private fun observeLoginResult() {
        viewModel.state.observe(requireActivity()){
            when(it){
                LoginState.LOADING -> binding.progress.isVisible = true
                LoginState.SUCCESS -> loginSuccess()
                LoginState.ERROR -> renderError()
            }
        }
    }

    private fun renderError() = with(binding){
        progress.isVisible = false
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }
    private fun setButtons() = with(binding) {
        buttonLoginGoogle.setOnClickListener {
//            checkLogin()
            loginSuccess()
        }
        buttonLoginFacebook.setOnClickListener {
            checkFacebook()
        }
    }

    private fun checkLogin() {
        facebookHasCall = false
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 120)
    }

    private fun checkFacebook() {
        facebookHasCall = true
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                viewModel.getFacebookLogin(result.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(requireActivity(), "error.toString()", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(requireActivity(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data)
        if (requestCode == 120 && resultCode == -1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            viewModel.getGoogleLogin(account.idToken!!)
        } else if (facebookHasCall) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun loginSuccess() = with(binding) {
        progress.isVisible = false
        saveUser()
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}

