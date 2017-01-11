/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.sdk;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 *
 * @author Shall
 */
public class CisBioxSDK implements ICisBioxSDK {

    private static CisBioxSDK instance;

    private final ICisBioxSDK dll = ICisBioxSDK.INSTANCE;

    @Override
    public int CIS_SDK_Biometrico_Iniciar() {
        int iRetorno = dll.CIS_SDK_Biometrico_Iniciar();
        return iRetorno;
    }

    @Override
    public int CIS_SDK_Biometrico_Finalizar() {
        int iRetorno = dll.CIS_SDK_Biometrico_Finalizar();
        return iRetorno;
    }

    @Override
    public int CIS_SDK_Biometrico_LerDigital(PointerByReference pTemplate) {
        int iRetorno = dll.CIS_SDK_Biometrico_LerDigital(pTemplate);
        return iRetorno;
    }

    @Override
    public Pointer CIS_SDK_Biometrico_LerDigital_RetornoPonteiro(IntByReference iRetorno) {
        Pointer pDigital;
        pDigital = dll.CIS_SDK_Biometrico_LerDigital_RetornoPonteiro(iRetorno);
        return pDigital;
    }

    @Override
    public int CIS_SDK_Biometrico_CancelarLeitura() {
        int iRetorno = dll.CIS_SDK_Biometrico_CancelarLeitura();
        return iRetorno;
    }

    @Override
    public int CIS_SDK_Biometrico_CompararDigital(PointerByReference pAmostra1, PointerByReference pAmostra2) {
        int iRetorno = dll.CIS_SDK_Biometrico_CompararDigital(pAmostra1, pAmostra2);
        return iRetorno;
    }

    @Override
    public Pointer CIS_SDK_Biometrico_LerWSQ(IntByReference iRetorno, IntByReference iSize) {
        Pointer pDigital;
        pDigital = CIS_SDK_Biometrico_LerWSQ(iRetorno, iSize);
        return pDigital;
    }

    @Override
    public int CIS_SDK_Biometrico_LerDigitalComImagem(Pointer pTemplate, IntByReference iTemplate, Pointer pImagem, IntByReference iImagem, int iFundoBranco, int iTipoImagem) {
        int iRetorno = dll.CIS_SDK_Biometrico_LerDigitalComImagem(pTemplate, iTemplate, pImagem, iImagem, iFundoBranco, iTipoImagem);
        return iRetorno;
    }

    @Override
    public Pointer CIS_SDK_Versao() {
        Pointer pDigital;
        pDigital = dll.CIS_SDK_Versao();
        return pDigital;
    }

    public static CisBioxSDK getInstance() {
        instance = new CisBioxSDK();
        return instance;
    }

    
}
