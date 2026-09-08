package com.ads.restaurante;

import com.ads.restaurante.dto.cliente.ClienteRequest;
import com.ads.restaurante.dto.pedido.PedidoRequest;
import com.ads.restaurante.dto.pedido.PedidoResponse;
import com.ads.restaurante.dto.prato.PratoRequest;
import com.ads.restaurante.model.PedidoStatus;
import com.ads.restaurante.service.ClienteService;
import com.ads.restaurante.service.PedidoService;
import com.ads.restaurante.service.PratoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PedidoServiceTest {

    @Autowired
    ClienteService clienteService;
    @Autowired
    PratoService pratoService;
    @Autowired
    PedidoService pedidoService;

    @Test
    void pedido_calcula_total_no_servidor_e_comeca_pendente() {
        var cliente = clienteService.criar(new ClienteRequest(
                "ana", "senha12345", "ana@ex.com", null, null));
        var prato = pratoService.criar(new PratoRequest(
                "Lasanha", new BigDecimal("30.00"), true));

        PedidoResponse pedido = pedidoService.criar(
                new PedidoRequest(cliente.id(), prato.id(), 3));

        assertThat(pedido.valorTotal()).isEqualByComparingTo("90.00");
        assertThat(pedido.status()).isEqualTo(PedidoStatus.PENDENTE);
        assertThat(pedido.clienteUsername()).isEqualTo("ana");
    }

    @Test
    void prato_indisponivel_nao_gera_pedido() {
        var cliente = clienteService.criar(new ClienteRequest(
                "bruno", "senha12345", "bruno@ex.com", null, null));
        var prato = pratoService.criar(new PratoRequest(
                "Fora do cardápio", new BigDecimal("10.00"), false));

        assertThatThrownBy(() -> pedidoService.criar(
                new PedidoRequest(cliente.id(), prato.id(), 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void pedido_entregue_nao_pode_ser_cancelado() {
        var cliente = clienteService.criar(new ClienteRequest(
                "carla", "senha12345", "carla@ex.com", null, null));
        var prato = pratoService.criar(new PratoRequest(
                "Risoto", new BigDecimal("40.00"), true));
        var pedido = pedidoService.criar(new PedidoRequest(cliente.id(), prato.id(), 1));

        pedidoService.atualizarStatus(pedido.id(), PedidoStatus.ENTREGUE);

        assertThatThrownBy(() -> pedidoService.cancelar(pedido.id()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
