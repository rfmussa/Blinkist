package renato.com.blinkist.ui

/**
 * A view contract in a MVI architecture.
 * **This** contract can be implemented by an android component.
 *
 * The `view` is tied to an UI `model` [M] that it can render.
 */
interface MviView<in M> {
  /** State render function. Default [Unit]. */
  fun render(model: M) = Unit
  fun actions(model: M) = Unit
}
