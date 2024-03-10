package hw2.service.ui

import hw2.exception.ToPreviousWindowException
import hw2.service.ui.window.Window

class UIServiceImpl(private val StartWindow: Window) : UIService {
    override fun start() {
        StartWindow.run()
    }
}