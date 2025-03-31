import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuShortcut,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { LogOut, Settings, User2Icon } from "lucide-react";
import { useEffect, useState } from "react";
import { SettingsDialog } from "./SettingsDialog";
import { logout } from "@/services/authService";
import { getMe } from "@/services/userService";
import { User } from "@/types/user";
import { toast } from "sonner";
import { AxiosError } from "axios";
import { useNavigate } from "react-router-dom";

export function Profile() {
  const navigate = useNavigate();
  const [isSettingsOpen, setIsSettingsOpen] = useState(false);
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const fetchUser = async () => {
      await getMe()
        .then((data) => {
          setUser(data);
        })
        .catch((error) => {
          console.error(error);
        });
    };
    fetchUser();
  }, []);

  const handleSignOut = async () => {
    try {
      await logout();
      navigate("/signin");
    } catch (error) {
      const err = error as AxiosError;
      toast.error(err.message);
    }
  };

  const handleSettingsOpen = (e: Event) => {
    e.preventDefault();
    setIsSettingsOpen(true);
  };

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="outline">
          <User2Icon className="h-4 w-4" />
          Perfil
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent className="w-56">
        <DropdownMenuLabel>
          {user?.firstName} {user?.lastName}
        </DropdownMenuLabel>
        <DropdownMenuSeparator />
        <DropdownMenuGroup>
          <DropdownMenuItem onSelect={handleSettingsOpen}>
            <div className="flex items-center justify-between w-full">
              Configurações
              <DropdownMenuShortcut>
                <Settings className="h-4 w-4" />
              </DropdownMenuShortcut>
            </div>
          </DropdownMenuItem>
          <DropdownMenuItem onClick={handleSignOut}>
            Sair
            <DropdownMenuShortcut>
              <LogOut className="h-4 w-4" />
            </DropdownMenuShortcut>
          </DropdownMenuItem>
        </DropdownMenuGroup>
      </DropdownMenuContent>
      <SettingsDialog open={isSettingsOpen} onOpenChange={setIsSettingsOpen} />
    </DropdownMenu>
  );
}
