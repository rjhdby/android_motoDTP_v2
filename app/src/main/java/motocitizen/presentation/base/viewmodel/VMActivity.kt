package motocitizen.presentation.base.viewmodel

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import motocitizen.presentation.base.BaseActivity
import motocitizen.presentation.base.viewmodel.commands.CommandsLiveData
import motocitizen.presentation.base.viewmodel.commands.VMCommand

abstract class VMActivity<VM : BaseViewModel> : BaseActivity() {

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    @CallSuper
    open fun onCommandReceived(command: VMCommand) {}

    @CallSuper
    protected open fun initViewModel() {
        viewModel.commands.subscribeCommand {
            onCommandReceived(it)
        }
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        this.observe(this@VMActivity, { action.invoke(it) })
    }

    private fun <T> CommandsLiveData<T>.subscribeCommand(action: (T) -> Unit) {
        this.observe(this@VMActivity, Observer { commands ->
            if (commands == null) {
                return@Observer
            }
            var command: T?
            do {
                command = commands.poll()
                if (command != null) {
                    action.invoke(command)
                }
            } while (command != null)
        })
    }
}