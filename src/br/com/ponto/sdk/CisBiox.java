/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.sdk;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 *
 * @author Shall
 */
public class CisBiox {

    CisBioxSDK sdk = CisBioxSDK.getInstance();

    private Pointer digital;
    private int resultado;

    public byte[] capturarDigital() {
        IntByReference iRetorno = new IntByReference();
        this.digital = this.lerDigitalRetornoPonteiro(iRetorno);
        this.resultado = iRetorno.getValue();
        return digital.getByteArray(0, 669);
    }

    public int iniciar() {
        int iRetorno = sdk.CIS_SDK_Biometrico_Iniciar();
        this.resultado = iRetorno;
        return iRetorno;
    }

    public int finalizar() {
        int iRetorno = sdk.CIS_SDK_Biometrico_Finalizar();
        this.resultado = iRetorno;
        return iRetorno;
    }

    public int lerDigital(PointerByReference pTemplate) {
        int iRetorno = sdk.CIS_SDK_Biometrico_LerDigital(pTemplate);
        return iRetorno;
    }

    private Pointer lerDigitalRetornoPonteiro(IntByReference iRetorno) {
        Pointer pDigital;
        pDigital = sdk.CIS_SDK_Biometrico_LerDigital_RetornoPonteiro(iRetorno);
        return pDigital;
    }

    private int cancelarLeitura() {
        int iRetorno = sdk.CIS_SDK_Biometrico_CancelarLeitura();
        return iRetorno;
    }

    private Pointer lerDigitalLerWSQ(IntByReference iRetorno, IntByReference iSize) {
        Pointer pImagem;
        pImagem = sdk.CIS_SDK_Biometrico_LerWSQ(iRetorno, iSize);
        return pImagem;
    }

    public String versao() {
        Pointer p;
        p = sdk.CIS_SDK_Versao();
        return p.getString(0);
    }

    public static String mensagem(int iRetorno) {
        String mensagem = "ERRO NÃO REGISTRADO";
        switch (iRetorno) {
            case 0:
                mensagem = "COMANDO NÃO EXECUTADO";
                break;
            case 1:
                mensagem = "COMANDO EXECUTADO COM SUCESSO";
                break;
            case -1:
                mensagem = "LEITOR INCOMPATIVEL COM SDK";
                break;
            case -2:
                mensagem = "DIGITAIS NÃO SÃO IGUAIS";
                break;
            case -10:
                mensagem = "ERRO DESCONHECIDO";
                break;
            case -11:
                mensagem = "FALTA DE MEMÓRIA";
                break;
            case -12:
                mensagem = "ARGUMENTO INVÁLIDO";
                break;
            case -13:
                mensagem = "LEITOR EM USO";
                break;
            case -14:
                mensagem = "TEMPLATE INVÁLIDO";
                break;
            case -15:
                mensagem = "ERRO INTERNO";
                break;
            case -16:
                mensagem = "NÃO HABILITADO PARA CAPTURAR DIGITAL";
                break;
            case -17:
                mensagem = "CANCELADO PELO USUÁRIO";
                break;
            case -18:
                mensagem = "LEITURA NÃO POSSÍVEL";
                break;
            case -21:
                mensagem = "ERRO DESCONHECIDO";
                break;
            case -22:
                mensagem = "SDK NÃO FOI INICIADO";
                break;
            case -23:
                mensagem = "LEITOR NÃO CONECTADO";
                break;
            case -24:
                mensagem = "ERRO NO LEITOR";
                break;
            case -25:
                mensagem = "FRAME DE DADOS VAZIO";
                break;
            case -26:
                mensagem = "ORIGEM FALSA (FAKE)";
                break;
            case -27:
                mensagem = "HARDWARE INCOMPATIVEL";
                break;
            case -28:
                mensagem = "FIRMWARE INCOMPATIVEL";
                break;
            case -29:
                mensagem = "FRAME ALTERADO";
                break;
            default:
                break;
        }
        return mensagem;
    }
    
    public int compararDigital(byte[] digital1, byte[] digital2){
        PointerByReference recebeDigital1 = preparaDigital(digital1);
        PointerByReference recebeDigital2 = preparaDigital(digital2);
        int iRetorno = sdk.CIS_SDK_Biometrico_CompararDigital(recebeDigital1, recebeDigital2);
        return iRetorno;
    }
    
    private PointerByReference preparaDigital(byte[] buffer){
        final Pointer p = new Memory(669);
        p.write(0, buffer, 0, buffer.length);
        final PointerByReference pr = new PointerByReference();
        pr.setPointer(p);
        return pr;
    }

    public int getResultado() {
        return resultado;
    }
    
    
}
