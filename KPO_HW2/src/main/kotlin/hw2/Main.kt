package hw2

import hw2.create.WindowBuilder
import hw2.create.StartWindowBuilder
import hw2.service.ui.UIService
import hw2.service.ui.UIServiceImpl

fun main() {
    val startWindowBuilder: WindowBuilder = StartWindowBuilder()
    val uiService: UIService = UIServiceImpl(startWindowBuilder.create())
    uiService.start()
}