package br.edu.ifrn.ifjobs.controller;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifrn.ifjobs.dto.usuario.UsuarioGetDTO;
import br.edu.ifrn.ifjobs.dto.usuario.UsuarioInsertDTO;
import br.edu.ifrn.ifjobs.dto.usuario.UsuarioLoginGetDTO;
import br.edu.ifrn.ifjobs.exception.UsuarioNaoCadastradoException;
import br.edu.ifrn.ifjobs.exception.UsuarioNaoEncontradoException;
import br.edu.ifrn.ifjobs.model.Usuario;
import br.edu.ifrn.ifjobs.service.UsuarioService;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @CrossOrigin("*")
    @PostMapping("/create")
    public ResponseEntity<UsuarioGetDTO> createAluno(@RequestBody @Valid UsuarioInsertDTO dto) {
        Usuario usuarioSalvo;

        try {
            usuarioSalvo = usuarioService.create(dto);
        } catch (UsuarioNaoCadastradoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        UsuarioGetDTO dtoConvert = new UsuarioGetDTO();
        UsuarioGetDTO entityToDto = dtoConvert.convertEntityToDto(usuarioSalvo);

        return new ResponseEntity<>(entityToDto, HttpStatus.CREATED);
    }

    @GetMapping("/email/{email}")
    @CacheEvict(value = "usuarioEmail", allEntries = true)
    @CachePut(value = "usuarioEmail")
    public ResponseEntity<UsuarioGetDTO> buscaPorEmail(@PathVariable(name = "email") String email) {
        Usuario usuario;

        try {
            usuario = usuarioService.buscaPorEmail(email);
        } catch (UsuarioNaoEncontradoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        UsuarioGetDTO dtoConvert = new UsuarioGetDTO();
        UsuarioGetDTO entityToDto = dtoConvert.convertEntityToDto(usuario);

        return ResponseEntity.ok(entityToDto);
    }

    @GetMapping("/{id}")
    @CacheEvict(value = "usuario", allEntries = true)
    @CachePut(value = "usuario")
    public ResponseEntity<UsuarioGetDTO> buscarPorId(@PathVariable(name = "id") int id) {
        Usuario usuario;

        try {
            usuario = usuarioService.buscaPorId(id);
        } catch (UsuarioNaoEncontradoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        UsuarioGetDTO dtoConvert = new UsuarioGetDTO();
        UsuarioGetDTO entityToDto = dtoConvert.convertEntityToDto(usuario);

        return ResponseEntity.ok().body(entityToDto);
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<Usuario> buscarPorEmpresaId(@PathVariable(name = "id") int id) {
        Usuario usuario;

        try {
            usuario = usuarioService.buscaPorEmpresaId(id);
        } catch (UsuarioNaoEncontradoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.ok().body(usuario);
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioLoginGetDTO>> buscaUsuarios() {
        List<Usuario> usuarios = usuarioService.getAll();

        List<UsuarioLoginGetDTO> list = usuarios.stream().map(usuario -> {
            UsuarioLoginGetDTO u = new UsuarioLoginGetDTO();
            return u.convertEntityToDto(usuario);
        }).toList();

        return ResponseEntity.ok().body(list);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioGetDTO> atualizaCampo(@PathVariable(name = "id") int id,
            @RequestBody JsonPatch jsonPatch) {
        Usuario usuarioAtualizado;

        try {
            usuarioAtualizado = usuarioService.atualizaCampos(id, jsonPatch);
            System.out.println(usuarioAtualizado.getAluno().getDadosPessoa().getNome());
        } catch (UsuarioNaoEncontradoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (JsonProcessingException | IllegalArgumentException | JsonPatchException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }

        UsuarioGetDTO dtoConvert = new UsuarioGetDTO();
        UsuarioGetDTO entityToDto = dtoConvert.convertEntityToDto(usuarioAtualizado);

        return ResponseEntity.ok(entityToDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UsuarioGetDTO> deletaUsuario(UsuarioInsertDTO dto) {
        Usuario usuario = dto.convertDtoToEntity();
        Usuario usuarioDeletado;
        try {
            usuarioDeletado = usuarioService.delete(usuario);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        UsuarioGetDTO dtoConvert = new UsuarioGetDTO();
        UsuarioGetDTO entityToDto = dtoConvert.convertEntityToDto(usuarioDeletado);
        return ResponseEntity.ok().body(entityToDto);
    }
}
