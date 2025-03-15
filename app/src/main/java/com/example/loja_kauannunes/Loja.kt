package com.example.loja_kauannunes

import android.util.Log

const val TAG = "LojaApp"

class CoisaQueVendo(
    val codigo: Int,
    val nomeBonito: String,
    var precinho: Double,
    var quantiaNoEstoque: Int
) {
    fun mostrarInfo(): String {
        return """
            📦 Produto: $nomeBonito (ID: $codigo)
            💰 Custa só: R$ $precinho
            📊 Tem ainda: $quantiaNoEstoque unidades (pega antes que acabe!)
        """.trimIndent()
    }
}

class PessoaQueCompra(
    val identificador: Int,
    val nomeLindo: String,
    var dinheiros: Double,
    private val saldoInicial: Double
) {
    fun ganharDinheiros(): String {
        dinheiros = saldoInicial
        return "💰 Dinheiro resetado! Agora ${nomeLindo} tem R$ $dinheiros de novo!"
    }
}

class SacolaDasCompras {
    val coisinhas = mutableMapOf<CoisaQueVendo, Int>()

    fun botarNaSacola(coisa: CoisaQueVendo, quantia: Int): String {
        return if (coisa.quantiaNoEstoque >= quantia) {
            coisinhas[coisa] = coisinhas.getOrDefault(coisa, 0) + quantia
            coisa.quantiaNoEstoque -= quantia
            Log.d(TAG, "🛍️ ${coisa.nomeBonito} adicionado!")
            "✅ Adicionado $quantia x ${coisa.nomeBonito} na sacola!"
        } else {
            "❌ Acabou o estoque de ${coisa.nomeBonito}! Devia ter comprado antes!"
        }
    }

    fun verSacola(): String {
        return if (coisinhas.isEmpty()) {
            "🛒 Sacola vazia, bora gastar uns trocados!"
        } else {
            coisinhas.entries.joinToString("\n") { (coisa, quantia) ->
                "🔹 $quantia x ${coisa.nomeBonito} - R$ ${coisa.precinho} cada."
            }
        }
    }

    fun somarTudo(): Double {
        return coisinhas.entries.sumOf { (coisa, quantia) -> coisa.precinho * quantia }
    }

    fun limparSacola(): String {
        coisinhas.clear()
        return "🗑️ Carrinho limpo! Está pronto para mais compras!"
    }
}

class Lojinha(private val prateleira: MutableList<CoisaQueVendo>) {
    fun exibirTudo(): String {
        return "🏬 Produtos disponíveis:\n" +
                prateleira.joinToString("\n") { it.mostrarInfo() }
    }

    fun fecharCompra(cliente: PessoaQueCompra, sacola: SacolaDasCompras): String {
        val total = sacola.somarTudo()
        return if (total > cliente.dinheiros) {
            "❌ Ihh, faltou grana! Compra cancelada."
        } else {
            cliente.dinheiros -= total
            sacola.limparSacola()
            "🎉 Compra finalizada! Agora ${cliente.nomeLindo} tem R$ ${cliente.dinheiros}."
        }
    }
}
