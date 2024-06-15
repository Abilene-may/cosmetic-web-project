package org.example.cosmeticwebpro.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.common.ErrorDTO;
import org.example.cosmeticwebpro.models.request.RoleAndPermissionReqDTO;
import org.example.cosmeticwebpro.models.request.UserReqDTO;
import org.example.cosmeticwebpro.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/role")
public class RoleController {
  private final RoleService roleService;

  /**
   * API show detail a role and permission of a role
   */
  @GetMapping("/{roleId}")
  public ResponseEntity<Object> viewDetailARole(@PathVariable Long roleId){
    try {
      var user = roleService.viewRoleAndPermission(roleId);
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (CosmeticException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * API show detail a role and permission of a role
   */
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAllRoleAndPermission(){
    try {
      var allRoleAndPermission = roleService.getAllRoleAndPermission();
      return new ResponseEntity<>(allRoleAndPermission, HttpStatus.OK);
    } catch (CosmeticException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * update a role and permissions of a role
   */
  @PostMapping("/create")
  public ResponseEntity<Object> createRoleAndPermission(@RequestBody RoleAndPermissionReqDTO reqDTO){
    try{
      roleService.createARoleAndPermissions(reqDTO);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * update a role and permissions of a role
   */
  @PutMapping("/update")
  public ResponseEntity<Object> updateRoleAndPermissions(@RequestBody RoleAndPermissionReqDTO reqDTO){
    try{
      roleService.updateRoleOrPermission(reqDTO);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (CosmeticException e){
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex){
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


}
