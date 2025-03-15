package com.example.loja_kauannunes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loja_kauannunes.ui.theme.LojaKauanNunesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LojaKauanNunesTheme {
                LojaScreen()
            }
        }
    }
}

@Composable
fun LojaScreen() {
    val coisa1 = remember { CoisaQueVendo(1, "Notebook Gamer Super Ultra", 3000.0, 10) }
    val coisa2 = remember { CoisaQueVendo(2, "Mouse Óptico Turbo", 50.0, 5) }
    val coisa3 = remember { CoisaQueVendo(3, "Teclado Mecânico Barulhento", 100.0, 2) }

    val lojinha = remember { Lojinha(mutableListOf(coisa1, coisa2, coisa3)) }
    val cliente = remember { PessoaQueCompra(1, "Joãozin", 3500.0, 3500.0) }
    val sacola = remember { SacolaDasCompras() }

    var log by remember { mutableStateOf("🏬 Bem-vindo à lojinha!") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = log, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { log += "\n\n" + lojinha.exibirTudo() }, modifier = Modifier.weight(1f)) {
                Text("Mostrar Produtos")
            }
            Button(onClick = { log += "\n\n" + sacola.limparSacola() }, modifier = Modifier.weight(1f)) {
                Text("Limpar Carrinho")
            }
        }

        Row {
            Button(onClick = { log += "\n\n" + sacola.botarNaSacola(coisa1, 1) }, modifier = Modifier.weight(1f)) {
                Text("Comprar Notebook")
            }
            Button(onClick = { log += "\n\n" + sacola.botarNaSacola(coisa2, 1) }, modifier = Modifier.weight(1f)) {
                Text("Comprar Mouse")
            }
        }

        Row {
            Button(onClick = { log += "\n\n" + sacola.botarNaSacola(coisa3, 1) }, modifier = Modifier.weight(1f)) {
                Text("Comprar Teclado")
            }
            Button(onClick = { log += "\n\n🛒 Sacola:\n" + sacola.verSacola() }, modifier = Modifier.weight(1f)) {
                Text("Ver Sacola")
            }
        }

        Row {
            Button(onClick = { log += "\n\n🤑 Total: R$ ${sacola.somarTudo()}" }, modifier = Modifier.weight(1f)) {
                Text("Ver Total")
            }
            Button(onClick = { log += "\n\n" + lojinha.fecharCompra(cliente, sacola) }, modifier = Modifier.weight(1f)) {
                Text("Finalizar Compra")
            }
        }

        Row {
            Button(onClick = { log = "🏬 Bem-vindo à lojinha!" }, modifier = Modifier.weight(1f)) {
                Text("Limpar Tela")
            }
            Button(onClick = { log += "\n\n" + cliente.ganharDinheiros() }, modifier = Modifier.weight(1f)) {
                Text("Resetar Dinheiro")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LojaPreview() {
    LojaKauanNunesTheme {
        LojaScreen()
    }
}
