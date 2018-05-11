package renato.com.blinkist.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import renato.com.blinkist.ui.MviView

abstract class MviPresenter<in V : MviView<*>> {

  fun bind(view: V): Disposable {
    // Create a new composite disposable for the binding and pass it to the business logic implementation.
    val disposable = CompositeDisposable()
    onBind(view, disposable)
    return disposable
  }

  /**
   * Called when the view is bound to this Presenter.
   * All reactive subscriptions should be added to the provided disposable.
   */
  protected abstract fun onBind(view: V, disposable: CompositeDisposable): Disposable
}
