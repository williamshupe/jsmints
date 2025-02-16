package com.zegreatrob.minreact

import com.zegreatrob.minreact.external.corejs.objectAssign
import js.core.JsoDsl
import react.Children
import react.ChildrenBuilder
import react.ElementType
import react.FC
import react.Key
import react.Props
import react.PropsWithChildren
import react.ReactNode
import react.create

inline fun <reified P : DataProps<P>> tmFC(crossinline function: ChildrenBuilder.(P) -> Unit):
    ElementType<DataPropsBridge> = FC { props: DataPropsBridge ->
    val newProps = ensureKotlinClassProps(props, P::class.js)
    +newProps.unsafeCast<Props>()
    function(newProps)
}

fun <P : DataProps<P>> ensureKotlinClassProps(props: DataPropsBridge, jsClass: JsClass<P>): P =
    if (props::class.js == jsClass) {
        props
    } else {
        @Suppress("UNUSED_VARIABLE")
        val thing = jsClass
        val newProps = js("new thing()")
        objectAssign(newProps, props)
        newProps
    }.unsafeCast<P>()

interface DataProps<P : DataProps<P>> {
    val component: TMFC
}

external interface DataPropsBridge : Props

typealias TMFC = ElementType<DataPropsBridge>

val DataProps<*>.children get() = this.unsafeCast<PropsWithChildren>().children

fun ChildrenBuilder.children(DataProps: DataProps<*>) {
    Children.toArray(DataProps.children).forEach(::child)
}

fun <P : DataProps<P>> create(dataProps: DataProps<P>, block: @JsoDsl Props.() -> Unit = {}) =
    dataProps.component.create {
        +dataProps.unsafeCast<Props>()
        block(unsafeCast<Props>())
    }

fun <D : DataProps<in D>> DataProps<in D>.create(
    key: Key? = null,
    block: @JsoDsl
    (ChildrenBuilder.() -> Unit) = {}
): ReactNode {
    val dataProps = this
    return component.create {
        +dataProps.unsafeCast<Props>()
        key?.let { this.key = it }

        block()
    }
}
