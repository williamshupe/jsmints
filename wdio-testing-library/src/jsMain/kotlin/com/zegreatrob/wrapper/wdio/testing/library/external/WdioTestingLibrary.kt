@file:JsModule("@testing-library/webdriverio")
@file:JsNonModule

package com.zegreatrob.wrapper.wdio.testing.library.external

import com.zegreatrob.wrapper.wdio.Browser
import com.zegreatrob.wrapper.wdio.Element
import kotlin.js.Promise

external fun setupBrowser(browser: Browser): TestingLibraryBrowser

external interface TestingLibraryBrowser {
    fun getByText(text: String): Promise<Element>
    fun getAllByText(text: String): Promise<Array<Element>>
    fun findByText(text: String): Promise<Element>
    fun findAllByText(text: String): Promise<Array<Element>>
    fun queryByText(text: String): Promise<Element?>
    fun queryAllByText(text: String): Promise<Array<Element>>

    fun getByRole(role: String, options: RoleOptions): Promise<Element>
    fun getAllByRole(role: String, options: RoleOptions): Promise<Array<Element>>
    fun findByRole(role: String, options: RoleOptions): Promise<Element>
    fun findAllByRole(role: String, options: RoleOptions): Promise<Array<Element>>
    fun queryByRole(role: String, options: RoleOptions): Promise<Element?>
    fun queryAllByRole(role: String, options: RoleOptions): Promise<Array<Element>>

    fun getByLabelText(text: String): Promise<Element>
    fun findByLabelText(text: String): Promise<Element>
    fun findByDisplayValue(value: String): Promise<Element>
}

external fun within(element: Element): TestingLibraryBrowser

external interface RoleOptions {
    var name: String
}
