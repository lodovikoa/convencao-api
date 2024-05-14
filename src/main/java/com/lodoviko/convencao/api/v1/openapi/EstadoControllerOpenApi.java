package com.lodoviko.convencao.api.v1.openapi;

import com.lodoviko.convencao.api.exceptionhandler.Problem;
import com.lodoviko.convencao.api.v1.dto.input.EstadoInputDTO;
import com.lodoviko.convencao.api.v1.dto.model.EstadoDTO;
import com.lodoviko.convencao.core.openapi.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;

@Tag(name = "Estados")
public interface EstadoControllerOpenApi {

    @Operation(summary = "Listar Estados")
    @PageableParameter
    public PagedModel<EstadoDTO> listar(@PageableDefault(size = 10) @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Buscar Estado pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do Estado inválido", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))}),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))})
    })
    public EstadoDTO buscar(Long sqEstado);

    @Operation(summary = "Cadastrar novo Estado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estado criado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EstadoDTO.class))})
    })
    public EstadoDTO cadastrar(EstadoInputDTO estadoInputDTO);

    @Operation(summary = "Alterar um Estado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado atualizado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EstadoDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))})
    })
    public EstadoDTO alterar(Long sqEstado, EstadoInputDTO estadoInputDTO);

    @Operation(summary = "Excluir um Estado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado excluído", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))})
    })
    public void excluir(Long sqEstado);
}
