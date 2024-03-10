package hw2.create

import hw2.service.ui.window.Window

interface WindowBuilder {
    fun create() : Window
}