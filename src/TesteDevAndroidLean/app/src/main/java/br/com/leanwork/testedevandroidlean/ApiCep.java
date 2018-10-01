package br.com.leanwork.testedevandroidlean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCep {

    @GET("{cep}/json")
    Call<CepResponse> pesquisarEndereco(
            @Path("cep") String cep);

}
