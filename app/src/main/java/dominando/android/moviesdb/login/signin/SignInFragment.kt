package dominando.android.moviesdb.login.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dominando.android.moviesdb.MainActivity
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentSignInBinding
import dominando.android.moviesdb.login.signup.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding
    private val viewModel: SignUpViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderLoginResult()
        setButtons()
    }

    private fun setButtons() {
        binding.buttonGoogle.setOnClickListener {
            checkLogin()
        }
        binding.buttonFacebook.setOnClickListener {
            checkFacebook()
        }
    }

    private fun checkLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 120)
    }

    private fun checkFacebook() {
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
        if (requestCode == 120) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            viewModel.getGoogleLogin(account.idToken!!)
        } else callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun renderLoginResult() {
        viewModel.loginSuccess.observe(requireActivity()){
            if(it) loginSuccess()
        }
        viewModel.showLoad.observe(requireActivity()){
            progress.isVisible = it
        }
        viewModel.hasError.observe(requireActivity()){
            Toast.makeText(requireContext(), "Erro", Toast.LENGTH_SHORT).show()
            viewModel.hasError.value = false
        }
    }

    private fun loginSuccess() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}