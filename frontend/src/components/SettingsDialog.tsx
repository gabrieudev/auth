import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useAuth } from "@/providers/AuthContext";
import { forgotPassword, updateUser } from "@/services/userService";
import { useMutation } from "@tanstack/react-query";
import { Loader2, PenBoxIcon } from "lucide-react";
import { useState } from "react";
import { toast } from "sonner";
import { ThemeToggle } from "./ThemeToggle";
import { Button } from "./ui/button";

export function SettingsDialog({
  open,
  onOpenChange,
}: {
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
}) {
  const { user, isLoading } = useAuth();
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const updateUserMutation = useMutation({
    mutationFn: updateUser,
    onSuccess: () => {
      setIsEditDialogOpen(false);
      toast.success("Usuário atualizado com sucesso!");
      setTimeout(() => {
        window.location.reload();
      }, 2000);
    },
    onError: () => {
      toast.error("Erro ao atualizar o usuário");
    },
  });
  const forgotPasswordMutation = useMutation({
    mutationFn: forgotPassword,
    onSuccess: () => {
      toast.success("E-mail enviado com sucesso!");
    },
    onError: () => {
      toast.error("Erro ao enviar o e-mail");
    },
  });

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
                  {isLoading ? (
                    <Loader2 className="animate-spin" />
                  ) : (
                    <>
                      <p>
                        {user?.firstName} {user?.lastName}
                      </p>
                      <Button
                        onClick={() => setIsEditDialogOpen(true)}
                        variant="outline"
                      >
                        <PenBoxIcon />
                      </Button>
                    </>
                  )}
                </div>
              </div>

              <Dialog
                open={isEditDialogOpen}
                onOpenChange={setIsEditDialogOpen}
              >
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
                    <Button
                      onClick={() =>
                        updateUserMutation.mutate({
                          id: user?.id,
                          firstName,
                          lastName,
                        })
                      }
                      variant="default"
                    >
                      Salvar
                    </Button>
                  </div>
                </DialogContent>
              </Dialog>

              <div className="flex items-center justify-between">
                <h2 className="text-lg font-semibold">E-mail</h2>
                {isLoading ? (
                  <Loader2 className="animate-spin" />
                ) : (
                  <p>{user?.email}</p>
                )}
              </div>

              <div className="flex items-center justify-between">
                <h2 className="text-lg font-semibold">Redefinir senha</h2>
                <Button
                  onClick={() => forgotPasswordMutation.mutate()}
                  variant="outline"
                  disabled={forgotPasswordMutation.isPending}
                >
                  {forgotPasswordMutation.isPending ? (
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
