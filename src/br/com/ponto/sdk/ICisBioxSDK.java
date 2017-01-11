/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.sdk;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

/**
 *
 * @author Shall
 */
abstract interface ICisBioxSDK extends StdCallLibrary {

    ICisBioxSDK INSTANCE = (ICisBioxSDK)Native.loadLibrary("CIS_SDK", ICisBioxSDK.class);

    public int CIS_SDK_Biometrico_Iniciar();

    public int CIS_SDK_Biometrico_Finalizar();

    public int CIS_SDK_Biometrico_LerDigital(PointerByReference pTemplate);

    public Pointer CIS_SDK_Biometrico_LerDigital_RetornoPonteiro(IntByReference iRetorno);

    public int CIS_SDK_Biometrico_CancelarLeitura();

    public int CIS_SDK_Biometrico_CompararDigital(PointerByReference pAmostra1, PointerByReference pAmostra2);

    public Pointer CIS_SDK_Biometrico_LerWSQ(IntByReference iRetorno, IntByReference iSize);

    public int CIS_SDK_Biometrico_LerDigitalComImagem(Pointer pTemplate, IntByReference iTemplate, Pointer pImagem, IntByReference iImagem, int iFundoBranco, int iTipoImagem);

    public Pointer CIS_SDK_Versao();
}
