import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { forgotPassword, updateUser } from "@/services/userService";
import { AxiosError } from "axios";
import { Loader2, PenBoxIcon } from "lucide-react";
import { useState } from "react";
import { toast } from "sonner";
import { ThemeToggle } from "./ThemeToggle";
import { Button } from "./ui/button";
import { useAuth } from "@/hooks/useAuth";
import { ApiResponse } from "@/types/apiResponse";
import { User } from "@/types/user";

export function SettingsDialog({
  open,
  onOpenChange,
}: {
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
}) {
  const { user, loadingUser } = useAuth();
  const [loadingPassword, setLoadingPassword] = useState(false);
  const [editDialogOpen, setEditDialogOpen] = useState(false);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  const handleForgotPassword = async () => {
    setLoadingPassword(true);
    setTimeout(() => setLoadingPassword(false), 10000);
    try {
      await forgotPassword();
      toast.success("E-mail enviado com sucesso!");
    } catch (error) {
      const err = error as AxiosError;
      toast.error(err.message);
    }
  };

  const handleUpdateUser = async () => {
    try {
      const updatedUser = {
        id: user?.id,
        firstName,
        lastName,
      } as User;
      await updateUser(updatedUser);
      toast.success("Dados atualizados com sucesso!");
      setTimeout(() => window.location.reload(), 1500);
    } catch (error) {
      const err = error as ApiResponse<string>;
      toast.error(err.data || "Erro inesperado");
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogTrigger asChild></DialogTrigger>
      <DialogContent className="grid gap-4">
        <DialogHeader>
          <DialogTitle className="text-center">Configurações</DialogTitle>
        </DialogHeader>

        <Tabs defaultValue="account" aria-describedby="dialog-description">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="account">Conta</TabsTrigger>
            <TabsTrigger value="general">Geral</TabsTrigger>
          </TabsList>

          <TabsContent value="account">
            <div className="grid gap-4">
              <div className="flex items-center justify-between">
                <h2 className="text-lg font-semibold">Nome</h2>
                <div className="flex items-center gap-2">
                  {loadingUser ? (
                    <Loader2 className="animate-spin" />
                  ) : (
                    <>
                      <p>
                        {user?.firstName} {user?.lastName}
                      </p>
                      <Button
                        onClick={() => setEditDialogOpen(true)}
                        variant="outline"
                      >
                        <PenBoxIcon />
                      </Button>
                    </>
                  )}
                </div>
              </div>

              <Dialog open={editDialogOpen} onOpenChange={setEditDialogOpen}>
                <DialogContent>
                  <DialogHeader>
                    <DialogTitle className="mb-4">Editar Nome</DialogTitle>
                  </DialogHeader>
                  <div className="flex flex-col gap-6">
                    <input
                      type="text"
                      value={firstName}
                      onChange={(e) => setFirstName(e.target.value)}
                      placeholder="Nome"
                      className="input"
                    />
                    <input
                      type="text"
                      value={lastName}
                      onChange={(e) => setLastName(e.target.value)}
                      placeholder="Sobrenome"
                      className="input"
                    />
                    <Button onClick={handleUpdateUser} variant="default">
                      Salvar
                    </Button>
                  </div>
                </DialogContent>
              </Dialog>

              <div className="flex items-center justify-between">
                <h2 className="text-lg font-semibold">E-mail</h2>
                {loadingUser ? (
                  <Loader2 className="animate-spin" />
                ) : (
                  <p>{user?.email}</p>
                )}
              </div>

              <div className="flex items-center justify-between">
                <h2 className="text-lg font-semibold">Redefinir senha</h2>
                <Button
                  onClick={handleForgotPassword}
                  variant="outline"
                  disabled={loadingPassword}
                >
                  {loadingPassword ? (
                    <Loader2 className="h-4 w-4 animate-spin" />
                  ) : (
                    "Redefinir"
                  )}
                </Button>
              </div>
            </div>
          </TabsContent>

          <TabsContent value="general">
            <div className="grid gap-4">
              <div className="flex items-center justify-between">
                <h2 className="text-lg font-semibold">Tema</h2>
                <ThemeToggle />
              </div>
            </div>
          </TabsContent>
        </Tabs>
      </DialogContent>
    </Dialog>
  );
}
