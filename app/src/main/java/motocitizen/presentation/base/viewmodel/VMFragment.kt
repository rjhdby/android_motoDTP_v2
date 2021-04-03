package motocitizen.presentation.base.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import motocitizen.presentation.base.BaseFragment
import motocitizen.presentation.base.viewmodel.commands.CommandsLiveData
import motocitizen.presentation.base.viewmodel.commands.VMCommand

abstract class VMFragment<VM : BaseViewModel>(
    @LayoutRes contentLayoutId: Int
) : BaseFragment(contentLayoutId) {

    abstract val viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.commands.subscribeCommand {
            onCommandReceived(it)
        }
        viewModel.showError.subscribeCommand {
            Log.e("Motocitizen error", "${it.message}")
        }
        initViewModel()
    }

    abstract fun initViewModel()

    @CallSuper
    open fun onCommandReceived(command: VMCommand) {
        (baseActivity as? VMActivity<*>)?.onCommandReceived(command)
    }

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        this.observe(viewLifecycleOwner, { action.invoke(it) })
    }

    protected fun <T> CommandsLiveData<T>.subscribeCommand(action: (T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer { commands ->
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